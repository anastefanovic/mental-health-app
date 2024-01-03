import React, { useEffect, useState } from 'react';
import { useCookies } from 'react-cookie';

export default function Reviews() {
  const [isAdmin, setIsAdmin] = useState(false);
  const [isClient, setIsClient] = useState(false);
  const [unacceptedReviews, setUnacceptedReviews] = useState(null);
  const [acceptedReviews, setAcceptedReviews] = useState(null);
  const [allTherapistsToReview, setAllTherapistsToReview] = useState(null);
  const [chosenTherapist, setChosenTherapist] = useState(null);
  const [newReview, setNewReview] = useState("")
  const [reRender, setRerender] = useState(false);

  const [cookies] = useCookies();

  useEffect(() => {
    if (cookies && cookies.user) {

        switch (cookies.user.type) {
            case 'ADMIN': {
                setIsAdmin(true);

                fetch('http://localhost:8080/review/unaccepted/get', {
                    method: 'GET',
                    headers: { "Content-Type": "application/json" }
                }).then(res => {
                    return res.json();
                }).then(data => {
                    setUnacceptedReviews(data);
                    console.log("Unaccepted reviews: ", data);
                })
                return;
            }

            case 'CLIENT': {
                setIsClient(true);

                fetch('http://localhost:8080/review/accepted/get', {
                    method: 'GET',
                    headers: { "Content-Type": "application/json" }
                }).then(res => {
                    return res.json();
                }).then(data => {
                    setAcceptedReviews(data);
                    console.log("Accepted reviews: ", data);
                });

                fetch('http://localhost:8080/appointment/therapists/get/' + cookies.user.id, {
                  method: 'GET',
                  headers: { "Content-Type": "application/json" }
              }).then(res => {
                  return res.json();
              }).then(data => {
                  setAllTherapistsToReview(data);
                  console.log("Therspists to review: ", data);
              })

                return;
            }
        }
    }

    // guest or therapist
    fetch('http://localhost:8080/review/accepted/get', {
        method: 'GET',
        headers: { "Content-Type": "application/json" }
    }).then(res => {
        return res.json();
    }).then(data => {
        setAcceptedReviews(data);
        console.log("Accepted reviews: ", data);
    })
}, [reRender]);

const handleSend = async (e) => {
  e.preventDefault();

  const currentDate = new Date();

  const reviewToSend = {
      "reviewMessage": newReview,
      "therapist": JSON.parse(chosenTherapist)
  }

  setChosenTherapist(null);
  setNewReview(null);

  fetch('http://localhost:8080/review/add', {
      method: 'POST',
      body: JSON.stringify(reviewToSend),
      headers: { "Content-Type": "application/json" }
  }).then(res => {
      return res.json();
  }).then(data => {
      console.log('Review successfuly sent: ', data);
      setNewReview("");
      setChosenTherapist("");
      setRerender(!reRender);
  })
}

const handleAccept = (review) => {

  fetch('http://localhost:8080/review/accept/' + review.id, {
      method: 'PUT',
      headers: { "Content-Type": "application/json" }
  }).then(res => {
      return res.json();
  }).then(data => {
      console.log('Review successfuly accepted: ', data);
      setRerender(!reRender);
  })
}

const handleDelete = (review) => {
  fetch('http://localhost:8080/review/delete/' + review.id, {
      method: 'DELETE',
      headers: { "Content-Type": "application/json" }
  }).then(res => {
      console.log('Review successfuly deleted: ', review);
      setRerender(!reRender);
  })
}


  return (
    <div>

      {isAdmin &&
        <div className="accordion shadow-lg bg-body-tertiary ounded position-absolute p-3 w-75 translate-middle top-50 d-inline" id="accordionExample">
           {unacceptedReviews && unacceptedReviews.length > 0 && <h4 className="text-primary fw-semibold">Unaccepted reviews</h4>}
              {unacceptedReviews && unacceptedReviews.map((t) => (
                <ul className="list-group">
                  <div key={t.id}> 
                    <li className="list-group-item  text-sm-start ">
                      <div className="row">
                        <div className="col-md-10 mb-3 mt-3">{t.reviewMessage}</div>
                        <div className="col-md-1">
                          <button onClick={ () => handleDelete(t)} className="btn btn-outline-danger position-absolute mt-2 me-5">Delete</button>
                        </div>
                        <div className="col-md-1">
                          <button onClick={ () => handleAccept(t)} className="btn btn-outline-success position-absolute end-0 me-3 mt-2">Accept</button>
                        </div>
                      </div>
                    </li> 
                  </div>
                </ul> 
              ))} 
              
              {(!unacceptedReviews || unacceptedReviews.length === 0) &&
                <h4 className="text-danger fs-4"><br/>No unaccepted reviews to show</h4>
              }                               
        </div>
      }

      {!isAdmin && 
        <div className="accordion shadow-lg bg-body-tertiary rounded position-absolute p-3 w-75 translate-middle top-50 d-inline" id="accordionExample">
          <div>
            <h4 className="text-primary fw-semibold">Reviews</h4>
              <ul className="list-group">
              {acceptedReviews && acceptedReviews.map((t) => (
                <div key={t.id}> 
                  <li className="list-group-item border-1 rounded text-sm-start mb-1">
                    <div className="d-flex w-100 justify-content-between">
                      <div className="mb-1 text-muted">
                        About:&nbsp;
                        <a href={"/therapist-profile/"+t.therapist.id}>  {t.therapist.firstName} {t.therapist.lastName} </a>                   
                      </div> 
                      <small className="text-muted">{t.date}</small> 
                    </div>
                    <div className="mb-1 text-md">{t.reviewMessage}</div> 
                    <br/>
                  </li> 
                </div>
              ))}                                
              </ul>
          </div>
          {isClient && 
          <div >
            <br/><br/>
            <form onSubmit={handleSend} className="text-start">
                      <h4 className="text-">Write a review</h4>
                      <div className="row">
                          <div className="form-group col-md-12 mb-3">
                              <label className="form-label">Choose a therapist: </label>
                              <select required onChange={(e) => setChosenTherapist(e.target.value)} className="form-select" aria-label="Default select example">
                                  <option key="default" value="" selected>Therapist</option>
                                  {allTherapistsToReview && allTherapistsToReview.map((t) => (
                                  <option key={t.id} value={JSON.stringify(t)}>{t.firstName} {t.lastName}</option>
                                  ))}
                              </select>
                          </div>
                      </div>
                      <div className="row">
                          <div className="form-group col-md-12 mb-3">
                              <label className="form-label">Write a review: </label>
                              <textarea className="form-control" rows="3"
                                        placeholder="Review:"
                                        required
                                        value={newReview}
                                        onChange={(e) => setNewReview(e.target.value)}/>
                          </div>
                      </div>
                      <div className="text-left">
                          <button type="submit" className="btn btn-primary">Send</button>
                      </div>
            </form>
          </div>
          }
        </div>
      }
    </div>
  )
}
