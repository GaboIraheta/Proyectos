import { createContext, useEffect, useState } from 'react';

export const FetchContext = createContext({
    checklist: [],
    handleFetchChecklist: () => { },
    form: [],
    handleFetchForm: () => { },
    consultants: [],
    handleFetchConsultants: () => { },
    users: [],
    handleFetchUsers: () => { },
    getID: () => {}
});

export const FetchProvider = ({ children }) => {
    
    const [checklist, setCheklist] = useState(() => {
        const savedChecklist = sessionStorage.getItem('checklist');
        return savedChecklist ? JSON.parse(savedChecklist) : [];
    });
    const [form, setForm] = useState(sessionStorage.getItem('form'), []);
    const [consultants, setConsultants] = useState(sessionStorage.getItem('consultant'), []);
    const [users, setUsers] = useState(() => {
        const savedUsers = sessionStorage.getItem('users');
        return savedUsers ? JSON.parse(savedUsers) : [];
    });

    useEffect(() => {

        if (checklist) {
            sessionStorage.setItem('checklist', JSON.stringify(checklist));
        } else {
            sessionStorage.removeItem('checklist');
        }

        if (form) {
            sessionStorage.setItem('form', form);
        } else {
            sessionStorage.removeItem('form');
        }

        if (consultants) {
            sessionStorage.setItem('consultant', consultants);
        } else {
            sessionStorage.removeItem('consultant');
        }
        if (users) {
            sessionStorage.setItem('users', JSON.stringify(users));
        } else {
            sessionStorage.removeItem('users');
        }

    }, [checklist, form, consultants, users]);

    const handleFetchChecklist = (checklist, id) => {
        setCheklist(checklist);
        sessionStorage.setItem('checklistID', id);
    }

    const handleFetchForm = (form, id) => {
        setForm(form);
        sessionStorage.setItem('formID', id);
    }

    const handleFetchConsultants = (consultants, id) => {
        setConsultants(consultants);
        sessionStorage.setItem('consultantsID', id);
    }

    const handleFetchUsers = (users) => {
        setUsers(users);
    };

    const getID = (what) => {
        return sessionStorage.getItem(what);
    }

    return (
        <>
            <FetchContext.Provider value={{
                checklist,
                handleFetchChecklist,
                form,
                handleFetchForm,
                consultants,
                handleFetchConsultants,
                users,
                handleFetchUsers,
                getID
            }}>
                {children}
            </FetchContext.Provider>
        </>
    )
}