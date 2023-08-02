import axios from '../api/axios'
import { useEffect, useState } from 'react'

const useUsePost = (url, postData) => {
    console.log("Posting to " + url);
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        setLoading(true);
        console.log("Posting Started " );

        axios.post(url, postData).then((response) => {
                setData(response);
        }).catch((err) => {
            setError(err);
        }).finally(() => {

            setLoading(false);
            console.log("Posting Finished " );


        })
    }, [url]);

    return { data, loading, error };
}
export default useUsePost;