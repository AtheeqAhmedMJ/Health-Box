import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Consultation_Page.css';

const Consultaion_Page = () => {
  const navigate = useNavigate();

  const goToWithAppointment = () => {
    navigate('/consultation/with');
  };

  const goToWithoutAppointment = () => {
    navigate('/consultation/without');
  };

  return (
    <>
      <h1 className='Consultation'>CONSULTATION PANEL</h1>
      <div className="appointment-container">
        <div className="appointment-card" onClick={goToWithAppointment}>
          With Appointment
        </div>
        <div className="appointment-card" onClick={goToWithoutAppointment}>
          Without Appointment
        </div>
      </div>
    </>
  );
};

export default Consultaion_Page;
