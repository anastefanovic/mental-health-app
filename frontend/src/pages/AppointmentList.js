import React, { useEffect, useState } from 'react';
import { useCookies } from 'react-cookie';
import { Link } from 'react-router-dom';

export default function AppointmentList() {
  const [appointmentList, setAppointmentList] = useState(null);
  const [isClient, setIsClient] = useState(false);
  const [isTherapist, setIsTherapist] = useState(false);
  const [reRender, setReRender] = useState(false);

  const [cookies] = useCookies();

  useEffect(() => {
    if (cookies && cookies.user.type === "CLIENT") {
      setIsClient(true);
    }
    if (cookies && cookies.user.type === "THERAPIST") {
      setIsTherapist(true);
    }

    fetch('http://localhost:8080/appointment/appointments', {
      method: 'POST',
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(cookies.user)
    }).then(res => {
      return res.json();
    }).then(data => {
      setAppointmentList(data);
      console.log("Appointment list: ", data)
    })
  }, [reRender]);

  const setAppointmentResponse = (id, response) => {

    const appointmentReply = {
      "first": id,
      "second": response
    };

    fetch('http://localhost:8080/appointment/reply', {
      method: 'PUT',
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(appointmentReply)
    }).then(res => {
      return res.json();
    }).then(data => {
      console.log("Appointment Reply: ", data)
      setReRender(!reRender);
    })
  }


  return (
    <div>
      <div className="accordion shadow-lg bg-body-tertiary rounded position-absolute p-3 w-75 translate-middle top-50 d-inline" id="accordionExample">
        {appointmentList && appointmentList.length > 0 && <h4 className="text-primary fw-semibold">All appointments</h4>}
        {appointmentList && appointmentList.length === 0 && <h4 className="text-danger fw-semibold">No appointments to show</h4>}
        <div className="accordion-item">
          {appointmentList && appointmentList.length > 0 && appointmentList.map((app) => (
            <div key={app.id}>

              <h2 className="accordion-header">
                <div className="accordion-button colapsed" type="button" data-bs-toggle="collapse" data-bs-target={`#collapseExample${app.id}`} aria-expanded="false" aria-controls={`#collapseExample${app.id}`} disabled>
                  <div>
                      <span className="fs-5">
                        {app.sessionDate}
                      </span>
  
                    {isTherapist && app.reply === "PENDING" &&
                      <span className="position-absolute end-0 me-5 top-0 mt-2">
                        <button onClick={() => setAppointmentResponse(app.id, "ACCEPTED")} type="button" className="btn btn-outline-success me-1">Accept</button>
                        <button onClick={() => setAppointmentResponse(app.id, "DECLINED")} type="button" className="btn btn-outline-danger ">Decline</button>
                      </span>
                    }  
                    {isTherapist && app.reply === "ACCEPTED" && <span className="position-absolute end-0 me-5 text-success">{app.reply}</span>}
                    {isTherapist && app.reply === "DECLINED" && <span className="position-absolute end-0 me-5 text-danger">{app.reply}</span>}
                    {isClient && app.reply === "PENDING" && <span className="position-absolute end-0 me-5 text-primary">{app.reply}</span>}
                    {isClient && app.reply === "ACCEPTED" && <span className="position-absolute end-0 me-5 text-success">{app.reply}</span>}
                    {isClient && app.reply === "DECLINED" && <span className="position-absolute end-0 me-5 text-danger">{app.reply}</span>}
                  </div>                    
                </div>
              </h2>

              {isClient &&
                <div id={`collapseExample${app.id}`} className="accordion-collapse collapse text-sm-start" data-bs-parent="#accordionExample">
                  <div className="accordion-body">
                    <span className=""><strong>Session time: </strong> {app.sessionTime} </span><br /><br />
                    <span><strong>Therapist: </strong><a href={"/therapist-profile/"+app.therapist.id}> {app.therapist.firstName} {app.therapist.lastName} </a></span><br /><br />
                    <span><strong>Address: </strong> {app.therapist.address} </span><br /><br />
                    <span><strong>Session Type: </strong> {app.sessionType} </span><br /><br />
                    <span><strong>Session Price: </strong> {app.therapist.sessionPrice} RSD</span><br />
                  </div>
                </div>
              }

              {isTherapist &&
                <div id={`collapseExample${app.id}`} className="accordion-collapse collapse text-sm-start" data-bs-parent="#accordionExample">
                  <div className="accordion-body">
                    <span className=""><strong>Session time: </strong> {app.sessionTime} </span><br /><br />
                    <span><strong>Session Type: </strong> {app.sessionType} </span><br /><br />
                    <span><strong>Client: </strong><Link to={"/client-profile/" + app.client.id}>{app.client.firstName} {app.client.lastName}</Link></span><br />
                  </div>
                </div>
              }
            </div>
          ))}
        </div>
      </div>
    </div>
  )
}
