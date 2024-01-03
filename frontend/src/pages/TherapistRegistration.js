import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export default function TherapistRegistration() {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [therapyType, setTherapyType] = useState(null);
    const [licenceInformation, setLicenceInformation] = useState('');
    const [about, setAbout] = useState('');
    const [address, setAddress] = useState('');
    const [sessionPrice, setSessionPrice] = useState('');

    const [allTherapyTypes, setAllTherapyTypes] = useState(null);

    const [errorMessage, setErrorMessage] = useState('');

    const navigate = useNavigate();

    useEffect(() => {
        fetch('http://localhost:8080/therapy-type/fetch', {
            method: 'GET',
            headers: { "Content-Type": "application/json" }
        }).then(res => {
            return res.json();
        }).then(data => {
            setAllTherapyTypes(data);
        })
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrorMessage('');

        const type = "THERAPIST";

        const newTherapist = { 
            "firstName": firstName,
            "lastName":  lastName, 
            "password": password,
            "email": email, 
            "phoneNumber": phoneNumber,
            "therapyType": JSON.parse(therapyType), 
            "licenceInformation": licenceInformation,
            "about": about,
            "address": address,
            "sessionPrice": sessionPrice,
            "type": type 
        };
        console.log("terpaut: ", newTherapist);
        
        fetch('http://localhost:8080/user/register', {
            method: 'POST',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(newTherapist)
          }).then(res => {
            console.log(res);
            if(res.status === 409) {
                setErrorMessage('A therapist with this email already exists');
                return;
            }
            if(!res.ok) {
                setErrorMessage('Unknown error');
                return;
            }
            console.log('New therapist registered', newTherapist);
            navigate('/therapist-list');
        }).catch( err => {
            console.log(err.message);
        });
      }

    return (
        <div className="auth-form-container d-inline-flex position-absolute translate-middle top-50 shadow bg-body-tertiary rounded p-3 mt-3">
            <h4 className="text-primary fw-semibold">Therapist sign up</h4>
            <form onSubmit = {handleSubmit}>
            <div className="row">
            <div className="form-group col-md-6 mb-3">
                <label className="form-label">First name: <strong>*</strong></label>
                <input type="text" className="form-control" placeholder="First name" 
                       required
                       value={firstName} 
                       onChange={(e) => setFirstName(e.target.value)}/>
            </div>
            <div className="form-group col-md-6 mb-3">
                <label className="form-label">Last name: <strong>*</strong></label>
                <input type="text" className="form-control" placeholder="Last name"
                       required
                       value={lastName}
                       onChange={(e) => setLastName(e.target.value)}/>
            </div>
            </div>

            <div className="row">
            <div className="form-group col-md-6 mb-3">
                    <label className="form-label">Email: <strong>*</strong></label>
                    <input type="email" className="form-control" placeholder="Email"
                           required
                           value={email}
                           onChange={(e) => setEmail(e.target.value)}/> 
            </div>
            <div className="form-group col-md-6 mb-3">
                    <label className="form-label">Password: <strong>*</strong></label>
                    <input type="password" className="form-control" placeholder="Password"
                           required
                           value={password}
                           onChange={(e) => setPassword(e.target.value)}/> 
            </div>
            </div>

            <div className="row">
            <div className="form-group col-md-6 mb-3">
                    <label className="form-label">Address:</label>
                    <input className="form-control" type="tel" placeholder="Address" 
                       value={address}
                       onChange={(e) =>  setAddress(() => (e.target.value))}/>
            </div>
            <div className="form-group col-md-6 mb-3">
                    <label className="form-label">Phone number: <strong>*</strong></label>
                    <input className="form-control" type="tel" placeholder="061222333" 
                       pattern="[0-9]*" 
                       value={phoneNumber}
                       onChange={(e) =>  setPhoneNumber(() => (e.target.validity.valid ? e.target.value : ''))}/>
            </div>
            </div>

            <div className="row">
            <div className="form-group col-12 mb-3">
                <label className="form-label">Therapy type: <strong>*</strong></label>
                <select required onChange={(e) => setTherapyType(e.target.value)} className="form-select" aria-label="Default select example">
                    <option key="default" value="" selected>Therapy type</option>
                    {allTherapyTypes && allTherapyTypes.map((t) => (
                        <option key={t.id} value={JSON.stringify(t)}>{t.shortName} - {t.fullName}</option>
                    ))}
                </select>
            </div>
            </div>

            <div className="row">
            <div className="form-group col-6 mb-3">
                <label className="form-label">Licence information: <strong>*</strong></label>
                <input type="text" className="form-control" placeholder="Licence information"
                        required
                        value={licenceInformation}
                        onChange={(e) => setLicenceInformation(e.target.value)}/> 
            </div>
            <div className="form-group col-6 mb-3">
                <label className="form-label">Price per session: <strong>*</strong></label>
                <input type="number" className="form-control" placeholder="Price per session"
                        required
                        min="0"
                        value={sessionPrice}
                        onChange={(e) => setSessionPrice(e.target.value)}/> 
            </div>
            </div>

            <div className="row">
            <div className="form-group col-12 mb-3">
                <label className="form-label">About</label>
                <textarea className="form-control" rows="3"
                          placeholder="About"
                          value={about}
                          onChange={(e) => setAbout(e.target.value)}/>
            </div>
            </div>

            <div className="row">
                {errorMessage && <div className="text-danger font-weight-light"> {errorMessage} </div>}
            </div>

            <div>
                <input className="btn btn-primary" type="submit" value="Sign up"/>
            </div>
            </form>
        </div>
    )
}
