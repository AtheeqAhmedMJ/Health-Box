import React from 'react';
import { useNavigate } from 'react-router-dom';
import Background from '../../components/backgrounds/Background';
import './Login_Page.css';

const Login_Page = () => {
  const navigate = useNavigate();

  const handleSignIn = () => {
    navigate('/welcome');
  };

  return (
    <>
      <div className="login-container">
        <h1 className="login-title">LOGIN</h1>
        <div className="login-form">
          <label className="login">EMAIL</label>
          <input type="text" placeholder="Enter email" className="login-input" />

          <label className="login">PASSWORD</label>
          <input type="password" placeholder="Enter password" className="login-input" />

          <button className="login-button" onClick={handleSignIn}>SIGN IN</button>
          <p className="login-forgot">Forgot password?</p>
        </div>
      </div>
    </>
  );
};

export default Login_Page;
