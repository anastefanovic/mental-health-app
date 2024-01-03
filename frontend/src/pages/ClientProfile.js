import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import moment from 'moment';
import { useCookies } from 'react-cookie';

export default function ClientProfile() {
    const[isClient, setIsClient] = useState(false);
    const[client, setClient] = useState(null);
    const[edit, setEdit] = useState(false);

    const { id } = useParams();
    const [cookies, setCookie] = useCookies();

    useEffect(() => {    
        if(cookies && cookies.user.type === "CLIENT") {
            setIsClient(true);
            setClient(cookies.user);
            return;

        }

        fetch('http://localhost:8080/user/client/' + id, {
          method: 'GET',
          headers: { "Content-Type": "application/json" }
        }).then(res => {
          return res.json();
        }).then(data => {
          setClient(data);
          console.log("Client: ", data)
        })

    }, []);

       const handleSubmit = async (e) => {
            e.preventDefault();
        
        fetch('http://localhost:8080/user/update', {
            method: 'PUT',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(client)
          }).then(res => {
            return res.json();
        }).then(data => {
            setClient(data);
            setCookie("user", data);
            setEdit(false);
            console.log("Updated client: ", data);
          })
        .catch( err => {
            console.log(err.message);
        });
      }

      const enableEdit = () => {
        const birthday = formatDate(client.birthday);

        setClient(c => ({
            ...c,
            birthday: birthday
         }));

         setEdit(true);
      }

      const formatDate = (date) => {
        const dd = date[0] + date[1];
        const mm = date[3] + date[4];
        const yyyy = date[6] + date[7] + date[8] + date[9];
        return (yyyy + "-" + mm + "-" + dd);
      }

  return (
    <div className="auth-form-container d-inline-flex position-absolute translate-middle top-50 shadow bg-body-tertiary rounded p-3 mt-3">
        {client && !edit && <div>
            <h2 className="text-primary fw-semibold">Client profile<br/><br/></h2>
            <div className="text-sm-start">
                <span><strong>Name: </strong> {client.firstName} {client.lastName} <br /><br /></span>
                <span><strong>Phone number: </strong> {client.phoneNumber} <br /><br /></span>
                <span><strong>Email: </strong> {client.email} <br /><br /></span>
                {client.birthday !== null && client.birthday !== "" && <span><strong>Birthday: </strong> {client.birthday} <br /><br /></span>}
                {client.gender !== null && client.gender !== "" && <span><strong>Gender: </strong> {client.gender} <br /><br /></span>}
            </div>
            {isClient && <button type="button" className="btn btn-primary" onClick={enableEdit}>Edit</button>}
        </div>}

        {client && edit && <div>
            <h2 className="text-primary fw-semibold">Client profile<br/><br/></h2>
            <form onSubmit = {handleSubmit}>
                <div className="row">
                    <div className="form-group col-md-4 mb-3">
                        <label className="form-label">First name: </label>
                    </div>
                    <div className="form-group col-md-8 mb-3">
                        <input type="text" className="form-control" 
                                value={client.firstName}
                                onChange={(e) => setClient(c => ({
                                    ...c,
                                    firstName: e.target.value
                        }))}/>
                    </div>
                </div>

                <div className="row">
                    <div className="form-group col-md-4 mb-3">
                        <label className="form-label">Last name: </label>
                    </div>
                    <div className="form-group col-md-8 mb-3">
                        <input type="text" className="form-control" 
                                value={client.lastName}
                                onChange={(e) => setClient(c => ({
                                    ...c,
                                    lastName: e.target.value
                        }))}/>
                    </div>
                </div>

                <div className="row">
                    <div className="form-group col-md-5 mb-3">
                        <label className="form-label">Phone number:</label>
                    </div>
                    <div className="form-group col-md-7 mb-3">
                        <input type="text" className="form-control" 
                                value={client.phoneNumber}
                                onChange={(e) => setClient(c => ({
                                    ...c,
                                    phoneNumber: e.target.value
                        }))}/>
                    </div>
                </div>

                <div className="row">
                    <div className="form-group col-md-3 mb-3">
                        <label className="form-label">Birthday: </label>
                    </div>
                    <div className="form-group col-md-9 mb-3">
                        <input type="date" className="form-control" 
                               max={moment().format("YYYY-MM-DD")}
                               value={client.birthday}
                               onChange={(e) => setClient(c => ({
                                ...c,
                                birthday: e.target.value
                        }))}/>
                    </div>
                </div>

                <div className="row">
                    <div className="col-md-12 mb-3" value={client.gender}  
                         onChange={(e) => setClient(c => ({
                                                        ...c,
                                                        gender: e.target.value
                    }))}>
                        <label className="mb-2 pb-1 me-5">Gender: </label>  
            
                        <div className="form-check form-check-inline me-5">
                            <input className="form-check-input" type="radio" name="gender" id="femaleGender" value="female"/>
                            <label className="form-check-label" htmlFor="femaleGender">Female</label>
                        </div>
                        <div className="form-check form-check-inline">
                            <input className="form-check-input" type="radio" name="gender" id="maleGender" value="male"/>
                            <label className="form-check-label" htmlFor="maleGender">Male</label>
                        </div>
                    </div>
                </div>

                <button type="submit" className="btn btn-primary">Save changes</button>
            </form>
        </div>}
    </div>
  )
}
