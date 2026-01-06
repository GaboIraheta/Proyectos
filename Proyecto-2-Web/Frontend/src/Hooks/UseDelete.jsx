import axios from 'axios';
import { useCallback, useState } from 'react';

const useDelete = (url) => {
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false); 

    const deleteData = useCallback(async (id, body, token, role) => {
        setLoading(true);
        setError(null);

        try {
            const data = await axios.delete(`${url}/${id}`, {
                data: body,
                headers: {
                    'Content-Type': 'application/json',
                    'authorization': `Bearer ${token}`,
                    'role': `Bearer ${role}`
                }
            });

            return data.data;

        } catch (error) {
            setError(handleErrors(error));
        } finally {
            setLoading(false);
        }
    }, [url]);

    const deleteUser = useCallback(async (id, token, role) => {

        try {
            const data = await axios.delete(`${url}/${id}`, {
                headers: {
                    'authorization': `Bearer ${token}`,
                    'role': `Bearer ${role}`
                }
            });

            return data.data;
        } catch(error) {
            setError(handleErrors(error));
        }
    }, [url]);

    return { deleteData, deleteUser, error, loading };
}

const handleErrors = (error) => {
    if (error.response) {
        if (error.response.data.message)
            return error.response.data.message;

        if (error.response.data.error) 
            return error.response.data.error.map(err => <p>{`${err.msg}\n`}</p>);
    } else {
        return 'Ha ocurrido un error en la eliminación.';
    }
}

export default useDelete;