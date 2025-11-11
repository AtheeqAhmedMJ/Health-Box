import React from 'react';
import Background from '../../components/backgrounds/Background';
import './Landing_Page.css';
import Logo from '../../components/logo/logo.jsx';
const Landing_Page = () => {
  return (
    <>
      <Background />
      <Logo />
      <h1 className="MT">HEALTH BOX</h1>
    </>
  );
};

export default Landing_Page;
