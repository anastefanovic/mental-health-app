import React, { useState } from 'react';
import { useCookies } from 'react-cookie';
import { useNavigate } from 'react-router-dom'

export default function PasswordChange() {
    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmNewPassword, setConfirmNewPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const [cookies, removeCookie] = useCookies();
    const navigate = useNavigate();

    // minimum length 8, at least one capital letter, at least one digit
    const passwordValidate = (newPassword) => {
        var regex = {
            capital:/(?=.*[A-Z])/,
            digit: /(?=.*[0-9])/,
            length: /(?=.{8,}$)/
        };

        return (
            regex.capital.test(newPassword) &&
            regex.digit.test(newPassword) &&
            regex.length.test(newPassword)
        );
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrorMessage('');

        if(cookies === null || cookies.user === null) {
            throw new Error("No logged user");
        }

        if(!passwordValidate(newPassword)) {
            setErrorMessage('New password must contain 8 characters or more, at least one capital letter and digit');
            return;
        }

        if(newPassword !== confirmNewPassword) {
            setErrorMessage('New password and confirmation password do not match.');
            return;
        }

        const userId = cookies.user.id;
        const userType = cookies.user.type;
    
        const passwordChange = {userId, userType, oldPassword, newPassword };
        
        fetch('http://localhost:8080/user/password', {
            method: 'PUT',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(passwordChange)
          }).then(res => {
            if (res.status === 403) {
                setErrorMessage('Old password is not correct');
                throw Error('Old password is not correct');
              }
            console.log('Password successfully changed', res.json());
            logout();
        }).catch( err => {
            console.log(err.message);
        });
      }

      const logout = () => {
        removeCookie("user");
        navigate('/login');
      }


  return (
    <div className="auth-form-container d-inline-flex position-absolute translate-middle top-50 shadow bg-body-tertiary rounded p-3">
        <h2 className="text-primary fw-semibold">Change password<br/><br/></h2>
        <form onSubmit = {handleSubmit}>
            <div className="row">
                <div className="form-group col-md-12 mb-3">
                    <label className="form-label">Old password: </label>
                    <input type="password" className="form-control" placeholder="Old password" 
                           value={oldPassword}
                           onChange={(e) => setOldPassword(e.target.value)}
                           required/>
                </div>
            </div>

            <div className="row">
                <div className="form-group col-md-6 mb-3">
                    <label className="form-label">New password:</label>
                    <input type="password" className="form-control" placeholder="New password" 
                           required
                           value={newPassword} 
                           onChange={(e) => setNewPassword(e.target.value)}/>
                </div>
                <div className="form-group col-md-6 mb-3">
                    <label className="form-label">Confirm new password:</label>
                    <input type="password" className="form-control" placeholder="Confirm new password"
                           required
                           value={confirmNewPassword}
                           onChange={(e) => setConfirmNewPassword(e.target.value)}/>
                </div>
            </div>


            <div className="row">
                <div className="fw-lighter">
                    Must be at least 8 characters long and contain at least 1 capital letter and digit. 
                    <br/>You will be automatically logged out after you change your password.
                </div>
                {errorMessage && <div className="text-danger font-weight-light"> {errorMessage} </div>}
            </div>

            <div className="mb-3 pt-3">
                <input className="btn btn-primary btn" type="submit" value="Save"/>
            </div>
        </form>
    </div>
  )
}
