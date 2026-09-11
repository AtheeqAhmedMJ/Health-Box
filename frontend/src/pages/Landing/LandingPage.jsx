import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import Background from '../../components/Background/Background';
import Logo from '../../components/Logo/Logo';

import './LandingPage.css';

const LandingPage = () => {
  const navigate = useNavigate();

  const [isHovered, setIsHovered] = useState(false);

  const handleLogoComplete = () => {
    navigate('/login');
  };

  return (
    <main className="landing-page">
      <Background />

      <section className="landing-content">

        <Logo
          onComplete={handleLogoComplete}
          interactive
          onHoverChange={setIsHovered}
        />

        <div
          className={`
            healthbox-text-container
            ${isHovered ? 'healthbox-text-visible' : ''}
          `}
        >
          <h1 className="landing-title">
            HEALTH BOX
          </h1>

          <p className="landing-subtitle">
            Click to enter
          </p>
        </div>

      </section>
    </main>
  );
};

export default LandingPage;