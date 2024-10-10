import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
    duration: '10s',
};

export default function () {
    let random_uuid = '';
    for (let i = 0; i < 4; i++) {
        random_uuid += generateHexRandomNumber(8);
        if (i !=3) random_uuid += '-';
        
    }
    //console.log(random_uuid);
    const response = http.post('http://192.168.0.91:8080', random_uuid, { 'Content-Type': 'application/json' });
    sleep(1);
}

function generateHexRandomNumber(length) {
    let result = '';
    const characters = '0123456789abcdef';

    for (let index = 0; index < length; index++) {
        result += characters.charAt(Math.floor(Math.random() * characters.length));
    }
    return result;
}