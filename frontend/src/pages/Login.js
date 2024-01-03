import React, { useState } from 'react'
import { Link } from 'react-router-dom'
import { useNavigate } from 'react-router-dom'
import { useCookies } from 'react-cookie';

export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [userType, setUserType] = useState('');
  
  const [errorMessage, setErrorMessage] = useState('');

  const [cookies, setCookie] = useCookies();

  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    setErrorMessage("");

    const loginCredentials = { email, password, userType };

    fetch('http://localhost:8080/user/login', {
      method: 'POST',
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(loginCredentials)
    }).then(res => {
      return res.json();
    }).then((data) => {
      if (data.validationFailed === true) {
        setErrorMessage('Invalid login credentials');
        return;
      }
      setCookie("user", data, { expires: new Date(Date.now() + 24 * 60 * 60 * 1000) });
      if (data.type === 'THERAPIST' && data.firstLogin) {
        navigate('/password');
      }
      else {
        navigate('/');
      }
    }).catch(err => {
      console.log(err.message);
    });
  }

  return (
    <div className="auth-form-container d-inline-flex position-absolute translate-middle top-50 bg-body-tertiary rounded p-3">
      <h2 className="text-primary fw-semibold">Login</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">Email: <strong>*</strong></label>
          <input type="email" className="form-control" placeholder="Email"
            required
            value={email}
            onChange={(e) => setEmail(e.target.value)} />
        </div>
        <div className="mb-3">
          <label className="form-label">Password: <strong>*</strong></label>
          <input type="password" className="form-control" placeholder="Password"
            required
            value={password}
            onChange={(e) => setPassword(e.target.value)} />
        </div>

        <div className="mb-3" value={userType} onChange={(e) => setUserType(e.target.value)}>
          <label className="mb-2 pb-1">User type: <strong>*</strong></label>
          <br />
          <div className="form-check form-check-inline">
            <input className="form-check-input" type="radio" name="userType" id="client" value="CLIENT" required />
            <label className="form-check-label" htmlFor="client">Client</label>
          </div>
          <div className="form-check form-check-inline">
            <input className="form-check-input" type="radio" name="userType" id="therapist" value="THERAPIST" required />
            <label className="form-check-label" htmlFor="therapist">Therapist</label>
          </div>
          <div className="form-check form-check-inline">
            <input className="form-check-input" type="radio" name="userType" id="admin" value="ADMIN" required />
            <label className="form-check-label" htmlFor="admin">Admin</label>
          </div>
        </div>

        <div className="mb-3">
          {errorMessage && <div className="text-danger font-weight-light"> {errorMessage} </div>}
        </div>
        <button type="submit" className="btn btn-primary">Login</button><br /><br />
        <div>
          <Link to="/client-registration" className="link-underline">
            Create account
          </Link>
        </div>
      </form>
    </div>
  )
}
