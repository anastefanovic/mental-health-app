import React, { useState } from 'react';
import moment from 'moment';
import { useNavigate } from 'react-router-dom';

export default function ClientRegistration() {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [birthday, setBirthday] = useState('');
    const [gender, setGender] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [passwordConfirm, setPasswordConfirm] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const navigate = useNavigate();

    // minimum length 8, at least one capital letter, at least one digit
    const passwordValidate = (password) => {
        var regex = {
            capital:/(?=.*[A-Z])/,
            digit: /(?=.*[0-9])/,
            length: /(?=.{8,}$)/
        };

        return (
            regex.capital.test(password) &&
            regex.digit.test(password) &&
            regex.length.test(password)
        );
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrorMessage('');

        if(!passwordValidate(password)) {
            setErrorMessage('Password pattern is not valid');
            return;
        }

        if(password !== passwordConfirm) {
            setErrorMessage('Password and confirmation password do not match. Please, try again.');
            return;
        }

        const type = "CLIENT";
        const newClient = { firstName, lastName, email, password, birthday, gender, phoneNumber, type };
        
        fetch('http://localhost:8080/user/register', {
            method: 'POST',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(newClient)
          }).then(res => {
            return res.json();
        }).then(data => {
            if(data.validationFailed) {
                setErrorMessage(data.validationMessage);
                return;
            }
            
            console.log('new client registered', data);
            navigate('/login');
        }).catch( err => {
            console.log(err.message);
        });
      }

    return (
        <div className="auth-form-container d-inline-flex position-absolute translate-middle top-50 shadow bg-body-tertiary rounded p-3">
            <h4 className="text-primary fw-semibold">Sign up</h4>
            <form onSubmit = {handleSubmit}>
            <br/>
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
                <label className="form-label">Birthday:</label>
                <input type="date" className="form-control" 
                       max={moment().format("YYYY-MM-DD")}
                       value={birthday}
                       onChange={(e) => setBirthday(e.target.value)}/>
            </div>
            <div className="col-md-6 mb-3" value={gender} onChange={(e) => setGender(e.target.value)}>
                <label className="mb-2 pb-1">Gender: </label>  
                <br/>
                    <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="gender" id="femaleGender" value="female"/>
                        <label className="form-check-label" htmlFor="femaleGender">Female</label>
                    </div>
                    <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="gender" id="maleGender" value="male"/>
                        <label className="form-check-label" htmlFor="maleGender">Male</label>
                    </div>
            </div>
            </div>

            <div className="row">
            <div className="form-group col-12 mb-3">
                    <label className="form-label">Email: <strong>*</strong></label>
                    <input type="email" className="form-control" placeholder="Email"
                           required
                           value={email}
                           onChange={(e) => setEmail(e.target.value)}/> 
            </div>
            </div>

            <div className="row">
            <div className="form-group col-md-6 mb-1">
                <label className="form-label">Password: <strong>*</strong></label>
                <input type="password" className="form-control" placeholder="Password"
                       required
                       value={password}
                       onChange={(e) => setPassword(e.target.value)}/>
            </div>
            <div className="form-group col-md-6 mb-1">
                <label className="form-label">Confirm password: <strong>*</strong></label>
                <input type="password" className="form-control" placeholder="Confirm password"
                       required
                       value={passwordConfirm}
                       onChange={(e) => setPasswordConfirm(e.target.value)}/>
            </div>
            </div>

            <div className="row">
                <div className="form-group col-md-12 mb-3">
                    <div className="form-text">
                        Must be at least 8 characters long and contain at least 1 capital letter and digit
                    </div>
                </div>
            </div>
        
            <div className="row">
            <div className="form-group col-12 mb-3">
                <label className="form-label">Phone number: <strong>*</strong></label>
                <input className="form-control" type="tel" placeholder="061222333" 
                       pattern="[0-9]*" 
                       required
                       value={phoneNumber}
                       onChange={(e) =>  setPhoneNumber(() => (e.target.validity.valid ? e.target.value : ''))}/>
            </div>
            </div>

            <div className="row">
                {errorMessage && <div className="text-danger font-weight-light"> {errorMessage} </div>}
            </div>

            <div className="mb-3 pt-3">
                <input className="btn btn-primary" type="submit" value="Sign up"/>
            </div>
            </form>
        </div>
    )
}
