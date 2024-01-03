import React, { useEffect, useState } from 'react';
import { useCookies } from 'react-cookie';
import { useParams } from 'react-router-dom';

export default function ClientProfile() {
    const [therapist, setTherapist] = useState(null);
    const [isTherapist, setIsTherapist] = useState(false);
    const [edit, setEdit] = useState(false);
    const [fullName, setFullName] = useState(null);

    const { id } = useParams();
    console.log(id);
    const [cookies, setCookie] = useCookies();
   
    useEffect(() => {  
        if(cookies && cookies.user !== null && cookies.user.type === "THERAPIST") {
            setIsTherapist(true);
            setFullName(cookies.user.firstName + " " + cookies.user.lastName);
        }

        fetch('http://localhost:8080/user/therapist/' + id, {
          method: 'GET',
          headers: { "Content-Type": "application/json" }
        }).then(res => {
          return res.json();
        }).then(data => {
          setTherapist(data);
          console.log("Therapist: ", data)
        })
    }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();

    fetch('http://localhost:8080/user/update', {
      method: 'PUT',
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(therapist)
    }).then(res => {
      return res.json();
    }).then(data => {
      setCookie("user", data);
      setTherapist(data);
      setEdit(false);
      console.log("Updated therapist: ", data);
    })
      .catch(err => {
        console.log(err.message);
      });
  }

  return (
    <div className="auth-form-container d-inline-flex position-absolute translate-middle top-50 shadow bg-body-tertiary rounded p-3 mt-3">
        {therapist &&  !edit && <div>
            {!isTherapist && <h4 className="text-primary fw-semibold">Therapist profile<br/><br/></h4>}
            {isTherapist && <h4 className="text-primary fw-semibold"> Client profile<br/><br/></h4>}
            <div className="text-sm-start">
                <span><strong>Name: </strong> {therapist.firstName} {therapist.lastName} <br /><br /></span>
                <span><strong>Email: </strong> {therapist.email} <br /><br /></span>
                <span><strong>Address: </strong> {therapist.address} <br /><br /></span>
                <span><strong>Phone number: </strong> {therapist.phoneNumber} <br /><br /></span>
                <span><strong>Licence information: </strong> {therapist.licenceInformation} <br /><br /></span>
                <span><strong>Price per session: </strong> {therapist.sessionPrice} RSD<br /><br /></span>
                <span><strong>Therapy type: </strong> {therapist.therapyType.fullName} - {therapist.therapyType.shortName}<br /><br /></span>
                <span><strong>About: </strong> {therapist.about} <br /><br /></span>
            </div>
            {isTherapist && <button type="button" className="btn btn-primary" onClick={(e) => setEdit(true)}>Edit</button>}
        </div>}

        {therapist && edit && <div>
            <h2 className="text-primary fw-semibold">My Profile<br/><br/></h2>
            <form onSubmit = {handleSubmit}>
              <div className="row">
                  <div className="form-group col-md-4 mb-3">
                      <label className="form-label">Full Name:</label>
                  </div>
                  <div className="form-group col-md-8 mb-3">
                      <input className="form-control" type="text" value={fullName} readOnly/>
                  </div>
              </div>

              <div className="row">
                  <div className="form-group col-md-3 mb-3">
                      <label className="form-label">Email:</label>
                  </div>
                  <div className="form-group col-md-9 mb-3">
                      <input className="form-control" type="text" value={therapist.email} readOnly/>
                  </div>
              </div>

              <div className="row">
                  <div className="form-group col-md-6 mb-3">
                      <label className="form-label">Licence Information:</label>
                  </div>
                  <div className="form-group col-md-6 mb-3">
                      <input className="form-control" type="text" value={therapist.licenceInformation} readOnly/>
                  </div>
              </div>

              <div className="row">
                  <div className="form-group col-md-4 mb-3">
                      <label className="form-label">Therapy Type:</label>
                  </div>
                  <div className="form-group col-md-8 mb-3">
                      <input className="form-control" type="text" value={therapist.therapyType.fullName} readOnly/>
                  </div>
              </div>

                <div className="row">
                    <div className="form-group col-md-3 mb-3">
                        <label className="form-label">Address: </label>
                    </div>
                    <div className="form-group col-md-9 mb-3">
                        <input type="text" className="form-control" 
                                value={therapist.address}
                                onChange={(e) => setTherapist(t => ({
                                    ...t,
                                    address: e.target.value
                        }))}/>
                    </div>
                </div>   

                <div className="row">
                    <div className="form-group col-md-5 mb-3">
                        <label className="form-label">Phone Number: </label>
                    </div>
                    <div className="form-group col-md-7 mb-3">
                        <input type="text" className="form-control" 
                                value={therapist.phoneNumber}
                                onChange={(e) => setTherapist(t => ({
                                    ...t,
                                    phoneNumber: e.target.value
                        }))}/>
                    </div>
                </div>   

                <div className="row">
                    <div className="form-group col-md-5 mb-3">
                        <label className="form-label">Price Per Session: </label>
                    </div>
                    <div className="form-group col-md-7 mb-3">
                    <input type="number" className="form-control" placeholder="Price"
                        required
                        min="0"
                        value={therapist.sessionPrice}
                        onChange={(e) => setTherapist(t => ({
                          ...t,
                          sessionPrice: e.target.value
                    }))}/> 
                    </div>
                </div>


                <div className="row">
                    <div className="form-group col-md-3 mb-3">
                        <label className="form-label">About:</label>
                    </div>
                    <div className="form-group col-md-9 mb-3">
                        <input type="text" className="form-control" 
                                value={therapist.about}
                                onChange={(e) => setTherapist(t => ({
                                    ...t,
                                    about: e.target.value
                        }))}/>
                    </div>
                </div>

                <button type="submit" className="btn btn-primary">Save Changes</button>
            </form>
        </div>}

    </div>
  )
}
