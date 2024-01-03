import React from 'react'
import { useCookies } from 'react-cookie';
import { Link, useNavigate } from 'react-router-dom'

export default function TherapistNavbar() {
    const [cookies, removeCookie] = useCookies();
    const navigate = useNavigate();

    const logout = () => {
        removeCookie("user");
        navigate('/login');
    }

    return (
        <div>
            <nav className="navbar shadow-lg navbar-expand-lg bg-body-tertiary">
                <div className="container-fluid">
                    <Link className="navbar-brand fw-semibold text-primary me-5" to="/">Home</Link>
                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                            <li className="nav-item me-4">
                                <Link className="nav-link active" aria-current="page" to="/therapist-list">Therapists</Link>
                            </li>
                            <li className="nav-item me-4">
                                <Link className="nav-link active" aria-current="page" to="/reviews">Reviews</Link>
                            </li>
                            <li className="nav-item me-4">
                                <Link className="nav-link active" aria-current="page" to="/questions-answers">Q&A</Link>
                            </li>
                        </ul>

                        {cookies && cookies.user &&
                            <div className="btn-group me-2 end-0">
                                <button type="button" className="btn btn-outline-primary">{cookies.user.firstName} {cookies.user.lastName}</button>
                                <button type="button" className="btn btn-outline-primary dropdown-toggle dropdown-toggle-split" data-bs-toggle="dropdown" aria-expanded="false">
                                    <span className="visually-hidden">Toggle Dropdown</span>
                                </button>
                                <ul className="dropdown-menu">
                                    <li><Link className="dropdown-item" to={"/therapist-profile/" + cookies.user.id}>Profile</Link></li>
                                    <li><Link className="dropdown-item" to="/appointment-list">My appointments</Link></li>
                                    <li><Link className="dropdown-item" to="/password">Change passwrod</Link></li>
                                    <li><button className="dropdown-item" onClick={logout}>Logout</button></li>
                                </ul>
                            </div>}
                    </div>
                </div>
            </nav>
        </div>
    )
}
