import { useEffect, useState } from "react"

//la url puede ser para obtener checklist, form o consultants
const useFetch = (url, token, role) => {
    const [data, setData] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);
    const [isAuth, setIsAuth] = useState(true);

    useEffect(() => {

       const fetchData = async () => {

            setLoading(true);
            setError(null);

            try {
                
                const response = await fetch(url, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'authorization': `Bearer ${token}`,
                        'role': `Bearer ${role}`
                    }
                });
                
                if (!response.ok) {
                    const error = await response.json();
                    setError(error.message);
                    setData(null);

                    if (response.status == 403) {
                        setIsAuth(false);
                    }
                    return;
                }

                const data = await response.json();
                setData(data);

            } catch (error) {
                if (error.response.message) {
                    setError(error.response.message || "Error desconocido");
                    return;
                } 
                setError('Ha ocurrido un error en la obtención de los datos.');
            } finally {
                setLoading(false);
            }
       } 

       if (url) fetchData();

    }, [url]);

    return { data, error, loading, isAuth, setIsAuth };
}

export default useFetch;