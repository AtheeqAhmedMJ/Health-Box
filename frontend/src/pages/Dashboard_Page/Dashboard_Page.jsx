import React, { useEffect, useState } from "react";
import AppointmentGraph from "../../components/AppointmentGraph/AppointmentGraph";
import { getDashboardSummary } from "../../services/dashboardApi";
import "./Dashboard_Page.css";

const Dashboard_Page = () => {
  const [analytics, setAnalytics] = useState({
    todayAppointments: 0,
    monthlyAppointments: 0,
    reoccurringPercent: 0,
    patientRecords: 0,
    graphData: []
  });

  useEffect(() => {
    async function fetchData() {
      try {
        const data = await getDashboardSummary();
        console.log("Fetched dashboard data:", data);

        setAnalytics({
          todayAppointments: data.todaysAppointments,
          monthlyAppointments: data.monthlyAppointments,
          reoccurringPercent: Number(data.recurringPatientsPercentage),
          patientRecords: data.totalPatients,
          graphData: Object.entries(data.graphData).map(([date, count]) => ({
            day: date,
            appointments: count
          }))
        });
      } catch (err) {
        console.error("Dashboard API error:", err);
      }
    }

    fetchData();
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
