import axios from "../api/axios";


class FileService {


    uploadImage(fileFormData, config){
        return axios.post('/api/upload/file-upload/image', fileFormData, config );
    }

    uploadCV(fileFormData, config){
        return axios.post('/api/upload/file-upload/resume', fileFormData, config );
    }
}

export default new FileService();