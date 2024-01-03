import React from 'react'
import { Link } from 'react-router-dom'

export default function Navbar() {

  
  return (
    <div>
        <nav className="navbar shadow-lg navbar-expand-lg bg-body-tertiary">
  <div className="container-fluid">
    <Link className="navbar-brand text-primary fw-semibold me-5" to="/">Home</Link>
    <div className="collapse navbar-collapse" id="navbarSupportedContent">
      <ul className="navbar-nav me-auto mb-2 mb-lg-0">
      <li className="nav-item">
        <Link className="nav-link active me-4" aria-current="page" to="/therapist-list">Therapists</Link>
        </li>
        <li className="nav-item">
        <Link className="nav-link active me-4" aria-current="page" to="/reviews">Reviews</Link>
        </li>
        <li className="nav-item">
        <Link className="nav-link active me-4" aria-current="page" to="/questions-answers">Q&A</Link>
        </li>
      </ul>
      <Link to="/login" className="btn btn-outline-primary me-1">Login</Link>
      <Link to="/client-registration" className="btn btn-outline-primary">Sign up</Link>
    </div>
  </div>
</nav>
    </div>
  )
}
