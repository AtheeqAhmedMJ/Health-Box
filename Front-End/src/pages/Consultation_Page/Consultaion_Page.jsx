import React from 'react'
import Background from '../../components/backgrounds/Background'
import SideBar from '../../components/SideBar/SideBar'
import './Consultation_Page.css';

const Consultaion_Page = () => {
  return (
    <>
      <h1 className='Consultation'>CONSULTATION  PANEL</h1>
     <div className="appointment-container">
      <div className="appointment-card">With Appointment</div>
      <div className="appointment-card">Without Appointment</div>
      </div>
    </>
  )
}

export default Consultaion_Page
