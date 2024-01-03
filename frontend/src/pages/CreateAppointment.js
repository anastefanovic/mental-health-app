import React, { useState, useEffect } from 'react';
import moment from 'moment';
import { useParams, useNavigate } from 'react-router-dom';
import { useCookies } from 'react-cookie';

export default function CreateAppointment() {
    const [date, setDate] = useState('');
    const [time, setTime] = useState('');
    const [sessionType, setSessionType] = useState('');
    const [availableWorkingHours, setAvailableWorkingHours] = useState('');
    const [therapist, setTherapist] = useState(null);
    const [therapistFullName, setTherapistFullName] = useState('');
    const [allTherapists, setAllTherapists] = useState(null);

    const [cookies] = useCookies();

    const navigate = useNavigate();
    const { id } = useParams();

     useEffect(() => {
        if(date === '' && id !== null && id !== undefined) {
            fetch('http://localhost:8080/user/therapist/' + id, {
                method: 'GET',
                headers: { "Content-Type": "application/json" }
              }).then(res => {
                return res.json();
              }).then(data => {
                console.log("Therapist: ", data);
                setTherapist(data);
                setTherapistFullName(data.firstName + ' ' + data.lastName);
              })
            
            return;
        }

        if(date === '' && id === undefined) {
            fetch('http://localhost:8080/user/therapists', {
                method: 'GET',
                headers: { "Content-Type": "application/json" }
              }).then(res => {
                return res.json();
              }).then(data => {
                console.log("All therapists: ", data);
                setAllTherapists(data);
              })
            
            return;
        }

         // fetch available working hours for specified therapist and date
         const therapistAndDate = {
            "therapistId": therapist.id,
            "date": date
        }
        fetch('http://localhost:8080/appointment/get-available-hours', {
            method: 'POST',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(therapistAndDate)
          }).then(res => {
            return res.json();
          }).then(data => {
            console.log("Slobodni termini: ", data);
            setAvailableWorkingHours(data);
          })
     }, [date]);


      const handleSubmit = async (e) => {
        e.preventDefault();

        const newAppointment = {
            "therapist": therapist,
            "client": cookies.user,
            "sessionType": sessionType,
            "sessionDate": date,
            "sessionTime": time
        }

        console.log(newAppointment);
    
        fetch('http://localhost:8080/appointment/create', {
          method: 'POST',
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(newAppointment)
        }).then(res => {
          return res.json();
        }).then((data) => {
          console.log("Successfully created appointment", data);
          navigate('/appointment-list');
        }).catch(err => {
          console.log(err.message);
        });
    
      }

  return (
    <div className="auth-form-container d-inline-flex position-absolute translate-middle top-50 shadow bg-body-tertiary rounded p-3 mt-3">
        <form onSubmit = {handleSubmit}>
        <div className="row">
            <div className="form-group col-md-12 mb-3">
                <h2 className="text-primary">Schedule an appointment</h2>
            </div>
        </div>

        { id !== undefined && therapist &&
            <div className="row">
                <div className="form-group col-md-12 mb-3">
                    <label className="form-label">Therapist:</label>
                    {therapistFullName && <input className="form-control" type="text" value={therapistFullName} readOnly/>}
                </div>
            </div>
        }
        { id === undefined && 
            <div className="row">
                <div className="form-group col-md-12 mb-3">
                    <label className="form-label">Therapist:</label>
                    <select required onChange={(e) => setTherapist(JSON.parse(e.target.value))} className="form-select" aria-label="Default select example 1">
                        <option key="default" value="" selected>Therapist</option>
                        {allTherapists && allTherapists.map((t) => (
                            <option key={t.id} value={JSON.stringify(t)}>{t.firstName} {t.lastName} - {t.therapyType.shortName}</option>
                        ))}
                    </select>
                </div>
            </div>
        }

            <div className="row">
                <div className="form-group col-md-12 mb-3">
                    <label className="form-label">Type of appointment:</label>
                    <select required onChange={(e) => setSessionType(e.target.value)} className="form-select" aria-label="Default select example 2">
                        <option key="default" value="" selected>Type of appointment</option>
                        <option key="online" value="ONLINE">Online</option>
                        <option key="in_person" value="IN_PERSON">In person</option>
                    </select>
                </div>
            </div>

           {therapist &&
            <div className="row">
                <div className="form-group col-md-12 mb-3">
                    <label className="form-label">Date:</label>
                    <input required type="date" className="form-control" 
                           min={moment().format("YYYY-MM-DD")}
                           value={date}
                           onChange={(e) => setDate(e.target.value)}/>
                </div>
            </div>
            }

            <div className="row">
                <div className="form-group col-md-12 mb-3">
                    {date && (availableWorkingHours.length > 0) && <div>
                        <label className="form-label">
                            Time: <br/>
                            <span className="fw-lighter">Only available times are shown</span>
                        </label>
                        <select required value={time} onChange={(e) => setTime(e.target.value)} className="form-select" aria-label="Default select example">
                            <option key="0" value="">Time</option>
                            {availableWorkingHours.map((t) => (
                                <option key={t.id} value={t}>{t}</option>))}
                        </select>
                    </div>}
                    {date && (availableWorkingHours.length === 0) && <label className="form-label text-danger"> 
                            The therapist is not available on the selected date. <br/>
                            Please, select another date.
                    </label>}
                </div>
            </div>

            <div className="mb-3">
                <input className="btn btn-primary" type="submit" value="Schedule"/>
            </div>
        </form>
    </div>
  )
}
