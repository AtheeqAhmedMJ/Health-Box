import React, { useState, useEffect } from "react";
import "./Calendar_Page.css";
import { useNavigate } from "react-router-dom";
import { createSlot } from "../../services/slotApi";
import { useClinic } from "../../context/useClinic";
import { getDaySchedule } from "../../services/calendarApi";

const Calendar_Page = () => {

  const navigate = useNavigate();
  const { clinicId, setActiveAppointment } = useClinic();

  const [selectedDate, setSelectedDate] = useState(
    new Date().toISOString().split("T")[0]
  );

  const [doctors, setDoctors] = useState([]);

  // ✅ FETCH DAILY SCHEDULE
  const fetchSchedule = async () => {
    if (!clinicId) return;

    try {
      const data = await getDaySchedule(clinicId, selectedDate);
      setDoctors(data);
    } catch (err) {
      console.error("Calendar API error:", err);
    }
  };

  useEffect(() => {
    fetchSchedule();
  }, [clinicId, selectedDate]);

  // ✅ SLOT CLICK HANDLER
  const handleSlotClick = async (doctor, slot) => {

    // ---------- BOOKED SLOT ----------
    if (slot.status === "BOOKED") {

      setActiveAppointment({
        doctorId: doctor.doctorId,
        appointmentId: slot.appointmentId,
        patientName: slot.patientName,
        time: slot.time,
        date: selectedDate
      });

      navigate("/consultation/with");
      return;
    }

    // ---------- FREE SLOT → CREATE SLOT ----------
    try {

      await createSlot({
        doctorId: doctor.doctorId,
        clinicId: clinicId,
        date: selectedDate,
        startTime: slot.time
      });

      alert("Slot created successfully");

      // reload schedule after creating slot
      fetchSchedule();

    } catch (err) {
      console.error(err);
      alert("Failed to create slot");
    }
  };

  return (
    <>
      <h1 className="Calendar">CALENDAR</h1>

      <div className="calendar-container">

        {/* DATE SELECTOR */}
        <div className="calendar-card">
          <h2>Select Date</h2>

          <input
            type="date"
            value={selectedDate}
            onChange={(e) => setSelectedDate(e.target.value)}
            className="date-input"
          />
        </div>

        {/* DOCTOR SCHEDULE */}
        <div className="calendar-card">
          <h2>Doctor Schedule</h2>

          <div className="doctor-wrapper">
            {doctors.map((doctor) => (
              <div key={doctor.doctorId} className="doctor-column">

                <h3>{doctor.doctorName}</h3>

                {doctor.slots.map((slot, i) => (
                  <div
                    key={i}
                    className={`slot ${
                      slot.status === "BOOKED"
                        ? "booked"
                        : slot.status === "AVAILABLE"
                        ? "available"
                        : "free"
                    }`}
                    onClick={() => handleSlotClick(doctor, slot)}
                    style={{
                      cursor:
                        slot.status === "BOOKED" ||
                        slot.status === "FREE"
                          ? "pointer"
                          : "default"
                    }}
                  >
                    <span>{slot.time}</span>
                    <span>
                      {slot.patientName
                        ? slot.patientName
                        : slot.status === "AVAILABLE"
                        ? "Available"
                        : "Free"}
                    </span>
                  </div>
                ))}

              </div>
            ))}
          </div>

        </div>

      </div>
    </>
  );
};

export default Calendar_Page;
