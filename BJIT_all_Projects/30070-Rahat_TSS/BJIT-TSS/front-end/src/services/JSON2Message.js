

const JSON2Message = (jsonstr) => {
    const object1 = JSON.parse(jsonstr);

    const object2 = Object.values(object1);

    const message = object2.map((value) => {
        return value;
    }).join('\n');

    return message;

}

export default JSON2Message