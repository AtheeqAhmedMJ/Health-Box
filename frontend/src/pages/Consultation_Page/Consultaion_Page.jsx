import React from "react";
import { useNavigate } from "react-router-dom";
import { useClinic } from "../../context/useClinic";
import "./Consultation_Page.css";

const Consultation_Page = () => {

  const navigate = useNavigate();
  const { activeAppointment } = useClinic();

  if (!activeAppointment) {
    return (
      <h2 style={{ textAlign: "center", marginTop: "100px" }}>
        No active appointment selected
      </h2>
    );
  }

  return (
    <>
      <h1 className="Consultation">CONSULTATION</h1>

      <div className="consultation-card">

        <h2>{activeAppointment.patientName}</h2>

        <p>Date: {activeAppointment.date}</p>
        <p>Time: {activeAppointment.time}</p>

        <button
          className="styled-button"
          onClick={() => navigate("/prescription")}
        >
          Start Consultation
        </button>

      </div>
    </>
  );
};

export default Consultation_Page;
