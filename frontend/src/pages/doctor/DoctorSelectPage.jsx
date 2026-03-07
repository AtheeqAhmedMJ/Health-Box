import React from "react";
import { useNavigate } from "react-router-dom";
import { useDoctor } from "../../context/DoctorContext";

const doctorsMock = [
  { id: 1, name: "Dr Shaik Sehran" },
  { id: 2, name: "Dr Ahmed" }
];

const DoctorSelectPage = () => {

  const navigate = useNavigate();
  const { setDoctor } = useDoctor();

  const selectDoctor = (doctor) => {
    setDoctor(doctor);
    navigate("/dashboard");
  };

  return (
    <div style={{ padding: "40px" }}>
      <h1>Select Doctor</h1>

      {doctorsMock.map((doc) => (
        <div
          key={doc.id}
          style={{
            padding: "20px",
            margin: "10px 0",
            background: "#222",
            color: "white",
            cursor: "pointer"
          }}
          onClick={() => selectDoctor(doc)}
        >
          {doc.name}
        </div>
      ))}
    </div>
  );
};

export default DoctorSelectPage;
