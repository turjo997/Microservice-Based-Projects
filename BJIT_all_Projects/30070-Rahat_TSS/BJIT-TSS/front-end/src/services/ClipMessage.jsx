
const ClipMessage = (string, maxLength) => {


    if (string.length <= maxLength) {
        return string;
    }

    return string.slice(0, maxLength - 3) + '...';


}

export default ClipMessage