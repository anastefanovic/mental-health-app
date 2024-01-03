import './App.css';
import '../node_modules/@popperjs/core/dist/umd/popper.min.js';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css'; 
import '../node_modules/bootstrap/dist/js/bootstrap.bundle.min';
import Navbar from './navbar/Navbar';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Reviews from './pages/Reviews';
import Home from './pages/Home';
import ClientRegistration from './pages/ClientRegistration';
import TherapistRegistration from './pages/TherapistRegistration';
import CreateAppointment from './pages/CreateAppointment';
import AppointmentList from './pages/AppointmentList';
import ClientProfile from './pages/ClientProfile';
import TherapistProfile from './pages/TherapistProfile';
import PasswordChange from './pages/PasswordChange';
import QuestionsAndAnswers from './pages/QuestionsAndAnswers';
import TherapistList from './pages/TherapistList';
import ClientNavbar from './navbar/ClientNavbar';
import { useEffect, useState } from 'react';
import TherapistNavbar from './navbar/TherapistNavbar';
import AdminNavbar from './navbar/AdminNavbar';
import { useCookies } from 'react-cookie';

function App() {
  const [isClient, setIsClient] = useState(false);
  const [isTherapist, setIsTherapist] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);
  const [isNone, setIsNone] = useState(false);

  const [cookies] = useCookies();

  useEffect(() => {
    setIsClient(false);
    setIsTherapist(false);
    setIsAdmin(false);
    setIsNone(false);

    if (cookies && cookies.user) {
      switch (cookies.user.type) {
          case 'ADMIN': {
              setIsAdmin(true);
              return;
            }
          case 'CLIENT': {
              setIsClient(true);
              return;
          }
          case 'THERAPIST': {
            setIsTherapist(true);
            return;
      }
      }
  }
  
  setIsNone(true);
  }, [cookies]);

  return (
    <Router>
      <div className="App">
        {isNone && <Navbar />}
        {isClient && <ClientNavbar />}
        {isTherapist && <TherapistNavbar />}
        {isAdmin && <AdminNavbar />}
        <div className="content">
          <Routes>
            <Route exact path="/" element={<Home />} />
            <Route exact path="/therapist-profile/:id" element={<TherapistProfile />} />
            <Route exact path="/reviews" element={<Reviews />} />
            <Route exact path="/therapist-list" element={<TherapistList />} />
            <Route exact path="/questions-answers" element={<QuestionsAndAnswers />} />
            <Route exact path="/therapist-registration" element={
              isAdmin ? <TherapistRegistration /> : <Navigate to="/"/>
            } />
            <Route exact path="/login" element={
              isNone ? <Login /> : <Navigate to="/"/>
            } />
            <Route exact path="/create-appointment/:id?"element={
              isClient ? <CreateAppointment /> : <Navigate to="/"/>
            } />
            <Route exact path="/appointment-list" element={
              !isNone ? <AppointmentList /> :  <Navigate to="/"/>
            } />
            <Route exact path="/client-profile/:id" element={
              (isClient || isTherapist) ? <ClientProfile /> : <Navigate to="/"/>
            } />
            <Route exact path="/password" element={
              !isNone ? <PasswordChange /> : <Navigate to="/"/>
            } />
            <Route exact path="/client-registration" element={
              isNone ? <ClientRegistration /> : <Navigate to="/"/>} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
