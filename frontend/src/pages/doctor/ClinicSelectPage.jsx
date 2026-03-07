import React from "react";
import { useNavigate } from "react-router-dom";
import { useClinic } from "../../context/useClinic";

const clinicsMock = [
  { id: 1, name: "Health Box Main Clinic" },
  { id: 2, name: "Evening Care Clinic" }
];

const ClinicSelectPage = () => {

  const navigate = useNavigate();
  const { setClinicId } = useClinic();

  const selectClinic = (clinic) => {
    setClinicId(clinic.id);
    navigate("/calendar");
  };

  return (
    <div style={{ padding: "40px" }}>
      <h1>Select Clinic</h1>

      {clinicsMock.map((clinic) => (
        <div
          key={clinic.id}
          style={{
            padding: "20px",
            margin: "10px 0",
            background: "#222",
            color: "white",
            cursor: "pointer"
          }}
          onClick={() => selectClinic(clinic)}
        >
          {clinic.name}
        </div>
      ))}
    </div>
  );
};

export default ClinicSelectPage;
