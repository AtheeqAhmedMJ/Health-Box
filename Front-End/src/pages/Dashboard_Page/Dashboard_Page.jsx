import React, { useEffect, useState } from "react";
import { Random } from "random-js";
import Background from "../../components/backgrounds/Background";
import SideBar from "../../components/SideBar/SideBar";
import AppointmentGraph from "../../components/AppointmentGraph/AppointmentGraph";
import "./Dashboard_Page.css";

const Dashboard_Page = () => {
  const random = new Random();

  const [analytics, setAnalytics] = useState({
    todayAppointments: 0,
    monthlyAppointments: 0,
    reoccurringPercent: 0,
    patientRecords: 0,
    graphData: [],
  });

  const generateFakeAnalytics = () => ({
    todayAppointments: random.integer(5, 20),
    monthlyAppointments: random.integer(100, 300),
    reoccurringPercent: random.integer(20, 70),
    patientRecords: random.integer(500, 1000),
    graphData: Array.from({ length: 7 }, (_, i) => ({
      day: `Day ${i + 1}`,
      appointments: random.integer(10, 30),
    })),
  });

  useEffect(() => {
    const updateAnalytics = () => setAnalytics(generateFakeAnalytics());
    updateAnalytics();
    const interval = setInterval(updateAnalytics, 5000);
    return () => clearInterval(interval);
  }, []);

  return (
    <>
      <h1 className="Dashboard">DASHBOARD</h1>
      <div className="dashboard-container">
        <div className="cards-wrapper">
          <div className="column">
            <div className="dashboard-card">
              <h2>Today’s Appointments</h2>
              <p>{analytics.todayAppointments}</p>
            </div>
            <div className="dashboard-card">
              <h2>Monthly Appointments</h2>
              <p>{analytics.monthlyAppointments}</p>
            </div>
          </div>

          <div className="column">
            <div className="dashboard-card">
              <h2>Patient Records</h2>
              <p>{analytics.patientRecords}</p>
            </div>
            <div className="dashboard-card">
              <h2>Reoccurring Patients</h2>
              <p>{analytics.reoccurringPercent}%</p>
            </div>
          </div>

          <div className="column">
            <div className="dashboard-card">
              <h2>Graph Overview</h2>
              <AppointmentGraph data={analytics.graphData} />
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default Dashboard_Page;
