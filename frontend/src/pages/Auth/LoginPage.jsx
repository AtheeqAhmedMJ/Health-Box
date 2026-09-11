import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';

import {
  FiMail,
  FiLock,
  FiAlertCircle
} from 'react-icons/fi';

import {
  authAPI,
  setAuthToken
} from '../../services/api';


import './LoginPage.css';

const LoginPage = () => {
  const navigate = useNavigate();

  const [loading, setLoading] = useState(false);

  const [error, setError] = useState('');

  const [formData, setFormData] = useState({
    username: '',
    password: '',
  });

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((previousData) => ({
      ...previousData,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    setError('');
    setLoading(true);

    try {
      const response = await authAPI.login(formData);

      const {
        token,
        username,
        role,
        hospitalId,
        phno,
      } = response.data;

      setAuthToken(token);

      localStorage.setItem(
        'user',
        JSON.stringify({
          username,
          role,
          hospitalId,
          phno,
        })
      );

      navigate('/dashboard');

    } catch (err) {
      setError(
        err.response?.data?.message ||
        'Login failed. Please check your credentials.'
      );

      console.error(
        'Login error:',
        err
      );

    } finally {
      setLoading(false);
    }
  };

  return (
    <>

      <main className="login-page">

        <section className="login-container">

          <h1 className="login-title">
            LOGIN
          </h1>

          <div className="login-form">

            {error && (
              <div className="login-error">

                <FiAlertCircle size={20} />

                <span>
                  {error}
                </span>

              </div>
            )}

            <form onSubmit={handleSubmit}>

              {/* USERNAME */}

              <div className="input-group">

                <label
                  className="login-label"
                  htmlFor="username"
                >
                  USERNAME
                </label>

                <div className="input-wrapper">

                  <FiMail
                    className="input-icon"
                    size={20}
                  />

                  <input
                    id="username"
                    type="text"
                    name="username"
                    value={formData.username}
                    onChange={handleChange}
                    required
                    className="login-input"
                    placeholder="Enter username"
                    autoComplete="username"
                  />

                </div>

              </div>

              {/* PASSWORD */}

              <div className="input-group">

                <label
                  className="login-label"
                  htmlFor="password"
                >
                  PASSWORD
                </label>

                <div className="input-wrapper">

                  <FiLock
                    className="input-icon"
                    size={20}
                  />

                  <input
                    id="password"
                    type="password"
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                    required
                    className="login-input"
                    placeholder="Enter password"
                    autoComplete="current-password"
                  />

                </div>

              </div>

              {/* OPTIONS */}

              <div className="login-options">

                <label className="remember-me">

                  <input type="checkbox" />

                  <span>
                    Remember me
                  </span>

                </label>

                <a
                  href="#forgot"
                  className="forgot-password"
                >
                  Forgot password?
                </a>

              </div>

              {/* LOGIN BUTTON */}

              <button
                type="submit"
                disabled={loading}
                className="login-button"
              >
                {loading
                  ? 'SIGNING IN...'
                  : 'SIGN IN'}
              </button>

            </form>

            {/* REGISTER */}

            <p className="register-text">

              Don't have an account?{' '}

              <Link to="/register">
                Register here
              </Link>

            </p>

          </div>

        </section>

      </main>
    </>
  );
};

export default LoginPage;