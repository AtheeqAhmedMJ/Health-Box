import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import './Sidebar.css';
import {
  FiHome,
  FiUserCheck,
  FiUsers,
  FiFileText,
  FiDollarSign,
  FiCreditCard,
  FiSettings,
  FiLogOut,
} from 'react-icons/fi';

const Sidebar = ({ userRole }) => {
  const [isExpanded, setIsExpanded] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();

  const isActive = (path) => location.pathname.startsWith(path);

  const handleLogout = () => {
    localStorage.removeItem('authToken');
    localStorage.removeItem('user');
    navigate('/login');
  };

  const items = [
    { icon: FiHome, label: 'Dashboard', path: '/dashboard' },
    { icon: FiUserCheck, label: 'Consultation', path: '/consultation' },
    { icon: FiUsers, label: 'Patients', path: '/patients' },
    { icon: FiFileText, label: 'Prescription', path: '/prescriptions' },
    { icon: FiDollarSign, label: 'Billing', path: '/billing' },
    { icon: FiCreditCard, label: 'Payments', path: '/payments' },
    ...(userRole === 'ADMIN' ? [{ icon: FiSettings, label: 'Settings', path: '/settings' }] : []),
  ];

  return (
    <div
      className={`hbx-sidebar ${isExpanded ? 'expanded' : ''}`}
      onMouseEnter={() => setIsExpanded(true)}
      onMouseLeave={() => setIsExpanded(false)}
    >
      <ul>
        {items.map((item) => {
          const Icon = item.icon;
          return (
            <li
              key={item.path}
              className={isActive(item.path) ? 'active' : ''}
              onClick={() => navigate(item.path)}
            >
              <Icon className="hbx-icon" />
              <span className="hbx-label">{item.label}</span>
            </li>
          );
        })}
        <li onClick={handleLogout}>
          <FiLogOut className="hbx-icon" />
          <span className="hbx-label">Logout</span>
        </li>
      </ul>
    </div>
  );
};

export default Sidebar;
