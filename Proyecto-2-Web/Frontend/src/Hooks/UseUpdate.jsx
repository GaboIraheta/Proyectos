import axios from 'axios';
import { useCallback, useState } from 'react';

const useUpdate = (url) => {
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

    const updateData = useCallback(async (id, body, token, role) => {
        setLoading(true);
        setError(null);
        
        try {

            const data = await axios.put(`${url}/${id}`, body, {
                headers: {
                    "Content-Type": "application/json",
                    "authorization": `Bearer ${token}`,
                    "role": `Bearer ${role}`
                }
            })

            return data.data;

        } catch (error) {
            if (error.response) {
                if (error.response.data.message) 
                    setError(error.response.data.message);

                if (error.response.data.error) 
                    setError(error.response.data.error.map(err => <p>{`${err.msg}\n`}</p>));
            } else {
                setError('Ha ocurrido un error en la actualización.')
            }
        } finally {
            setLoading(false);
        }
    }, [url]);

    // const recoverPassword = useCallback(asyn)

    return { updateData, error, loading };
}

export default useUpdate;