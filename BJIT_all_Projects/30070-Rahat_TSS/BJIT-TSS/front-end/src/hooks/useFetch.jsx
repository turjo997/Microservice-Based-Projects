import axios from '../api/axios'
import { useEffect, useState } from 'react'

const useFetch = (url) => {
    // console.log("Fetching " + url);
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        setLoading(true);
        // console.log("Loding Started " );

        axios.get(url).then((response) => {
                setData(response);
                console.log(response);
        }).catch((err) => {
            setError(err);
        }).finally(() => {

            setLoading(false);
            // console.log("Loding Finished " );


        })
    }, [url]);

    return { data, loading, error };
}
export default useFetch;