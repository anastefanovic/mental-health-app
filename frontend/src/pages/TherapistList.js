import React, { useEffect, useState } from 'react'
import { useCookies } from 'react-cookie';
import { useNavigate } from 'react-router-dom'

export default function TherapistList() {
  const [therapists, setTherapists] = useState(null);
  const [isAdmin, setIsAdmin] = useState(null);
  const [isClient, setIsClient] = useState(null);

  const navigate = useNavigate();
  const[cookies] = useCookies();

  useEffect(() => {  

    if(cookies && cookies.user) {
      if(cookies.user.type === "ADMIN") { setIsAdmin(true); }
      if(cookies.user.type === "CLIENT") { setIsClient(true); }
    }

    fetch('http://localhost:8080/user/therapists', {
      method: 'GET',
      headers: { "Content-Type": "application/json" }
    }).then(res => {
      return res.json();
    }).then(data => {
      setTherapists(data);
    })
  }, [therapists]);

  const deleteTherapist = (id) => {
    console.log("Deleting therapist with id ", id);

    fetch('http://localhost:8080/user/delete/' + id, {
      method: 'DELETE',
      headers: { "Content-Type": "application/json" }
    }).then(() => {
      // remove deleted therapist from the list
      setTherapists((current) => current.filter((t) => t.id !== id));
    })
  }

  const scheduleAppointment = (therapist) => {
    localStorage.setItem('chosen_therapist', JSON.stringify(therapist));
    navigate('/create-appointment/' + therapist.id);
  }

  return (
    <div >
    <div className="bg-body-tertiary accordion shadow-lg rounded position-absolute p-3 w-75 translate-middle top-50 d-inline" id="accordionExample">
    <h4 className="text-primary fw-semibold">Our therapists</h4>
      <div className="accordion-item">
        {therapists && therapists.map((t) => (
          <div key={t.id}>
            <h2 className="accordion-header">
              <div className="accordion-button colapsed" type="button" data-bs-toggle="collapse" data-bs-target={`#collapseExample${t.id}`} aria-expanded="false" aria-controls={`#collapseExample${t.id}`} disabled>
                <div>
                  <span className="fs-4 ">{t.firstName} {t.lastName}</span>
                  {isAdmin && <button onClick={() => deleteTherapist(t.id)} type="button" className="btn btn-outline-danger position-absolute end-0 me-5">Delete</button>}
                  {isClient && <button onClick={() => scheduleAppointment(t)} type="button" className="btn btn-outline-primary position-absolute end-0 me-5">Schedule</button>}
                  <br />
                  <span className="fs-6 text-secondary">{t.therapyType.shortName} - {t.therapyType.fullName}</span>
                </div>
              </div>
            </h2>
            <div id={`collapseExample${t.id}`} className="accordion-collapse collapse text-sm-start" data-bs-parent="#accordionExample">
              <div className="accordion-body">
                <span className=""><strong>Phone number: </strong> {t.phoneNumber} </span><br /><br />
                <span><strong>Email: </strong> {t.email} </span><br /><br />
                <span><strong>Address: </strong> {t.address} </span><br /><br />
                <span><strong>Licence information: </strong> {t.licenceInformation} </span><br /><br />
                <span><strong>Price per session: </strong> {t.sessionPrice} RSD</span><br /><br />
                <span><strong>About: </strong> {t.about} </span><br />
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
    </div>
  )
}
