import React from 'react';
import { Routes, Route, useLocation } from 'react-router-dom';

import Landing_Page from './pages/Landing_Page/Landing_Page';
import Login_Page from './pages/Login_Page/Login_Page';
import Welcome_Page from './pages/Welcome_Page/Welcome_Page';
import Dashboard_Page from './pages/Dashboard_Page/Dashboard_Page';
import Consultaion_Page from './pages/Consultation_Page/Consultaion_Page';
import WithAppointmentPage from './pages/WithAppointmentPage/WithAppointmentPage';
import WithoutAppointmentPage from './pages/WithoutAppointmentPage/WithoutAppointmentPage';
import Billing_Page from './pages/Billing_Page/Billing_Page';
import Prescription_Page from './pages/Prescription_Page/Prescription_Page';
import Printing_Page from './pages/Printing_Page/Printing_Page';
import Calendar_Page from './pages/Calendar_Page/Calendar_Page';

import SideBar from './components/SideBar/SideBar';
import Background from './components/backgrounds/Background';

const App = () => {

  const location = useLocation();

  // Pages where sidebar should NOT appear
  const hideSidebarPaths = ['/', '/login'];
  const hideSidebar = hideSidebarPaths.includes(location.pathname);

  return (
    <>
      <div style={{ display: 'flex' }}>

        {!hideSidebar && <SideBar />}

        <div style={{ flex: 1 }}>
          <Routes>

            {/* PUBLIC */}
            <Route path="/" element={<Landing_Page />} />
            <Route path="/login" element={<Login_Page />} />

            {/* AFTER LOGIN */}
            <Route path="/welcome" element={<Welcome_Page />} />

            {/* MAIN HMS */}
            <Route path="/dashboard" element={<Dashboard_Page />} />
            <Route path="/calendar" element={<Calendar_Page />} />

            <Route path="/consultation" element={<Consultaion_Page />} />
            <Route path="/consultation/with" element={<WithAppointmentPage />} />
            <Route path="/consultation/without" element={<WithoutAppointmentPage />} />

            <Route path="/prescription" element={<Prescription_Page />} />
            <Route path="/billing" element={<Billing_Page />} />
            <Route path="/printing" element={<Printing_Page />} />

            {/* FALLBACK */}
            <Route
              path="*"
              element={<h2 style={{ padding: '2rem' }}>404 - Page Not Found</h2>}
            />

          </Routes>
        </div>
      </div>

      {/* Global Background */}
      <Background />
    </>
  );
};

export default App;
