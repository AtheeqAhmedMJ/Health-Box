import React, { useState } from 'react';
import './SideBar.css';
import {
  FaHome,
  FaUserMd,
  FaFilePrescription,
  FaFileInvoiceDollar,
  FaPrint,
  FaUser,
} from 'react-icons/fa';
import { MdDashboard } from 'react-icons/md';
import { useNavigate, useLocation } from 'react-router-dom';

const SideBar = () => {
  const [isExpanded, setIsExpanded] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();

  const isActive = (path) => location.pathname === path;

  const handleNavigate = (path) => {
    navigate(path);
  };

  return (
    <div
      className={`side-bar ${isExpanded ? 'expanded' : 'collapsed'}`}
      onMouseEnter={() => setIsExpanded(true)}
      onMouseLeave={() => setIsExpanded(false)}
    >
      <ul>
        <li className={isActive('/') ? 'active' : ''} onClick={() => handleNavigate('/')}>
          <FaHome className="icon" />
          {isExpanded && <span>Home</span>}
        </li>
        <li className={isActive('/dashboard') ? 'active' : ''} onClick={() => handleNavigate('/dashboard')}>
          <MdDashboard className="icon" />
          {isExpanded && <span>Dashboard</span>}
        </li>
        <li className={isActive('/consultation') ? 'active' : ''} onClick={() => handleNavigate('/consultation')}>
          <FaUserMd className="icon" />
          {isExpanded && <span>Consultation</span>}
        </li>
        <li className={isActive('/prescription') ? 'active' : ''} onClick={() => handleNavigate('/prescription')}>
          <FaFilePrescription className="icon" />
          {isExpanded && <span>Prescription</span>}
        </li>
        <li className={isActive('/billing') ? 'active' : ''} onClick={() => handleNavigate('/billing')}>
          <FaFileInvoiceDollar className="icon" />
          {isExpanded && <span>Billing</span>}
        </li>
        <li className={isActive('/printing') ? 'active' : ''} onClick={() => handleNavigate('/printing')}>
          <FaPrint className="icon" />
          {isExpanded && <span>Printing</span>}
        </li>
        <li className={isActive('/user') ? 'active' : ''} onClick={() => handleNavigate('/user')}>
          <FaUser className="icon" />
          {isExpanded && <span>User</span>}
        </li>
      </ul>
    </div>
  );
};

export default SideBar;
