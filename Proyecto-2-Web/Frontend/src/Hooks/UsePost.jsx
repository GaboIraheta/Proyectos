import axios from 'axios';
import { useCallback, useState } from 'react'; 
import useUtil from './useUtil';

const usePost = (url) => {
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

    const { handleError } = useUtil();

    const postData = useCallback(async (id, body, token, role) => {
        setLoading(true);
        setError(null);

        try {

            const data = await axios.post(`${url}/${id}`, body, {
                headers: {
                    "Content-Type": 'application/json',
                    "authorization": `Bearer ${token}`,
                    "role": `Bearer ${role}`
                }
            })

            return data.data;

        } catch (error) {
            setError(handleError(error));
        } finally {
            setLoading(false);
        }
    }, [url]);

    const authentication = useCallback(async (body) => {
        setLoading(true);
        setError(null);

        try {

            const data = await axios.post(url, body, {
                headers: {
                    "Content-Type": "application/json",
                }
            });

            return data.data;

        } catch (error) {
            setError(handleError(error));
        } finally {
            setLoading(false);
        }
    }, [url]);

    const recoverPassword = useCallback(async (email, newPassword) => {
        setLoading(true);
        setError(null);

        try {
            
            console.log(url);
            const data = await axios.post(`${url}/recover-password`, { email }, {
                headers: {
                    "Content-Type": "application/json"
                },
                withCredentials: true
            });

            const response = data.data;
            console.log(response);

            const recover = await axios.post(`${url}/reset-password`, { newPassword }, {
                headers: {
                    "Content-Type": "application/json"
                },
                withCredentials: true
            });

            console.log(recover.data);
            return recover.data;
        } catch (error) {
            console.log(error);
            setError(handleError(error));
        } finally {
            setLoading(false);
        }
    }, []);

    return { postData, authentication, recoverPassword, error, loading };
}

export default usePost;