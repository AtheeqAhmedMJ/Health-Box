import React from 'react';
import { useLocation } from 'react-router-dom';
import { FiBell, FiUser } from 'react-icons/fi';

const Header = () => {
  const location = useLocation();
  
  // Format page title from path
  const getPageTitle = () => {
    const path = location.pathname;
    const title = path
      .split('/')
      .filter(Boolean)
      .join(' > ')
      .replace(/-/g, ' ')
      .toUpperCase();
    return title || 'Dashboard';
  };

  const getUserName = () => {
    try {
      const user = JSON.parse(localStorage.getItem('user'));
      return user?.name || 'User';
    } catch {
      return 'User';
    }
  };

  return (
    <>
      <style>{`
        .header-container {
          position: fixed;
          top: 0;
          left: 0;
          right: 0;
          height: 80px;
          background: rgba(255, 255, 255, 0.1);
          backdrop-filter: blur(10px);
          border-bottom: 1px solid rgba(255, 255, 255, 0.1);
          z-index: 30;
          display: flex;
          align-items: center;
          padding: 0 1.5rem;
          justify-content: space-between;
        }

        @media (min-width: 768px) {
          .header-container {
            left: 256px;
          }
        }

        .header-title {
          font-size: 1.5rem;
          font-weight: 700;
          color: #1f2937;
        }

        .header-actions {
          display: flex;
          align-items: center;
          gap: 1rem;
        }

        .header-notification-btn {
          padding: 0.5rem;
          background: transparent;
          border: none;
          border-radius: 0.5rem;
          cursor: pointer;
          transition: background 0.3s ease;
          color: #4b5563;
        }

        .header-notification-btn:hover {
          background: rgba(255, 255, 255, 0.2);
        }

        .header-user-section {
          display: flex;
          align-items: center;
          gap: 0.75rem;
          padding-left: 1rem;
          border-left: 1px solid rgba(255, 255, 255, 0.1);
        }

        .header-avatar {
          width: 40px;
          height: 40px;
          border-radius: 9999px;
          background: linear-gradient(to bottom right, #a78bfa, #fda4af);
          display: flex;
          align-items: center;
          justify-content: center;
          color: white;
        }

        .header-user-info {
          display: none;
        }

        @media (min-width: 640px) {
          .header-user-info {
            display: block;
          }
        }

        .header-user-name {
          font-size: 0.875rem;
          font-weight: 500;
          color: #1f2937;
          margin: 0;
        }

        .header-user-role {
          font-size: 0.75rem;
          color: #6b7280;
          margin: 0;
        }
      `}</style>
      <header className="header-container">
        <div>
          <h1 className="header-title">{getPageTitle()}</h1>
        </div>

        <div className="header-actions">
          {/* Notifications */}
          <button className="header-notification-btn">
            <FiBell size={20} />
          </button>

          {/* User Profile */}
          <div className="header-user-section">
            <div className="header-avatar">
              <FiUser size={20} />
            </div>
            <div className="header-user-info">
              <p className="header-user-name">{getUserName()}</p>
              <p className="header-user-role">Healthcare Provider</p>
            </div>
          </div>
        </div>
      </header>
    </>
  );
};

export default Header;
