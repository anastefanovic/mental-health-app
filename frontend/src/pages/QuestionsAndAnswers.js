import React, { useEffect, useState } from 'react';
import { useCookies } from 'react-cookie';

export default function QuestionsAndAnswers() {
    const [unansweredQuestions, setUnansweredQuestions] = useState(null);
    const [answeredQuestions, setAnsweredQuestions] = useState(null);
    const [newQuestion, setNewQuestion] = useState("");
    const [answer, setAnswer] = useState(null);
    const [isAdmin, setIsAdmin] = useState(false);
    const [isClient, setIsClient] = useState(false);
    const [selected, setSelected] = useState(null);
    const [reRender, setRerender] = useState(false);

    const [cookies] = useCookies();

    useEffect(() => {
        if (cookies && cookies.user) {

            switch (cookies.user.type) {
                case 'ADMIN': {
                    setIsAdmin(true);

                    fetch('http://localhost:8080/question/unanswered/get', {
                        method: 'GET',
                        headers: { "Content-Type": "application/json" }
                    }).then(res => {
                        return res.json();
                    }).then(data => {
                        setUnansweredQuestions(data);
                        console.log("Unanswered questions: ", data);
                    })
                    return;
                }

                case 'CLIENT': {
                    setIsClient(true);

                    fetch('http://localhost:8080/question/answered/get', {
                        method: 'GET',
                        headers: { "Content-Type": "application/json" }
                    }).then(res => {
                        return res.json();
                    }).then(data => {
                        setAnsweredQuestions(data);
                        console.log("Answered questions: ", data);
                    })
                    return;
                }
            }
        }

        // Guest or Therapist
        fetch('http://localhost:8080/question/answered/get', {
            method: 'GET',
            headers: { "Content-Type": "application/json" }
        }).then(res => {
            return res.json();
        }).then(data => {
            setIsAdmin(false);
            setIsClient(false);
            setAnsweredQuestions(data);
            console.log("Answered questions: ", data);
        })
    }, [reRender]);

    const handleSend = async (e) => {
        e.preventDefault();

        const askedQuestion = {
            "question": newQuestion,
            "answer": null
        }


        fetch('http://localhost:8080/question/add', {
            method: 'POST',
            body: JSON.stringify(askedQuestion),
            headers: { "Content-Type": "application/json" }
        }).then(res => {
            return res.json();
        }).then(data => {
            console.log('Question successfuly sent: ', data);
            console.log("New question: ", newQuestion);
            setNewQuestion("");
            setRerender(!reRender);
        })
    }

    const handleAnswer = async (e) => {
        e.preventDefault();

        const answeredQuestion = {
            "id": selected.id,
            "question": selected.question,
            "answer": answer
        }

        fetch('http://localhost:8080/question/answer', {
            method: 'PUT',
            body: JSON.stringify(answeredQuestion),
            headers: { "Content-Type": "application/json" }
        }).then(res => {
            return res.json();
        }).then(data => {
            console.log('Question successfuly answered: ', data);
            setSelected(null);
            setAnswer(null);
            setRerender(!reRender);
        })
    }

    const handleDelete = (question) => {
        fetch('http://localhost:8080/question/' + question.id, {
            method: 'DELETE',
            headers: { "Content-Type": "application/json" }
        }).then(res => {
            console.log('Question successfuly deleted: ', question);
            setSelected(null);
            setAnswer(null);
            setRerender(!reRender);
        })
    }


    return (
        <div>

            {isAdmin &&
            <div className="accordion bg-body-tertiary shadow-lg bg-body-tertiary rounded position-absolute p-3 w-75 translate-middle top-50 d-inline" id="accordionExample">
                <h4 className="text-primary fw-semibold">Unanswered Questions</h4>
                
                <ul className="list-group">
                {unansweredQuestions && unansweredQuestions.map((t) => (
                    <div key={t.id}> 
                        <li className="list-group-item  text-sm-start ">
                            <div className="row">
                                <div className="col-md-10 mt-2">
                                    {t.question} 
                                </div>
                                <div className="col-md-1">
                                    <button onClick={ () => handleDelete(t)} className="btn btn-outline-danger position-absolute mt-2">Delete</button>
                                </div>
                                <div className="col-md-1">
                                    <button onClick={ () => setSelected(t)} className="btn btn-outline-success position-absolute end-0 me-3 mt-2">Reply</button>
                                </div>
                            </div>
                            <br/>
                        </li> 
                    </div>
                ))}                                
                </ul>
                <br/>
                { selected && 
                    <form onSubmit={handleAnswer} className="text-start">
                        <div className="mb-3 text-sm-start">
                            <label className="form-label  fw-semibold">{selected.question}</label>
                            <textarea className="form-control" rows="3"
                                      placeholder="Answer:"
                                      required
                                      value={answer}
                                      onChange={(e) => setAnswer(e.target.value)} />
                        </div>
                        <div className="text-left">
                            <button type="submit" className="btn btn-primary">Save</button>
                        </div>
                    </form>
                }
     
            </div>
            }

            { !isAdmin && 
            <div className="accordion shadow-lg bg-body-tertiary rounded position-absolute p-3 w-75 translate-middle top-50 d-inline" id="accordionExample">
                {answeredQuestions && answeredQuestions.length > 0 && <h4 className="text-primary fw-semibold">Questions and answers</h4>}
                {answeredQuestions && answeredQuestions.length === 0 && <h4 className="text-danger fw-semibold">No answered questions to show</h4>}
                <div className="accordion-item">
                    {answeredQuestions && answeredQuestions.map((t) => (
                    <div key={t.id}>
                        <span className="accordion-header">
                            <div className="accordion-button colapsed" type="button" data-bs-toggle="collapse" data-bs-target={`#collapseExample${t.id}`} aria-expanded="false" aria-controls={`#collapseExample${t.id}`} disabled>
                                <div>
                                    <span className="fs-5">{t.question}</span>
                                </div>
                            </div>
                        </span>
            
                        <div id={`collapseExample${t.id}`} className="accordion-collapse collapse text-sm-start" data-bs-parent="#accordionExample">
                            <div className="accordion-body">
                                <span className=""> {t.answer} </span><br/><br/>
                            </div>
                        </div>
                    </div>
                    ))}
                </div>
                <br/>
                { isClient && 
                    <form onSubmit={handleSend} className="text-start">
                        <div className="mb-3">
                            <label className="form-label">Ask a question: </label>
                            <textarea className="form-control" rows="3"
                                      placeholder="Question:"
                                      required
                                      value={newQuestion}
                                      onChange={(e) => setNewQuestion(e.target.value)}/>
                        </div>
                        <div className="text-left">
                            <button type="submit" className="btn btn-primary">Send</button>
                        </div>
                    </form>
                }
            </div>
            }   

        </div>
    )
}
