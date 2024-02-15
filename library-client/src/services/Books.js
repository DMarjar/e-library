import axios from 'axios';

let API_URL = 'http://localhost:8081/api/books/page';

const getBooksPaginated = async (page, size, sort) => {
    const params = {
        page,
        size, sort
    };
    try {
        const response = await axios.post(API_URL, null, { params });
        return response.data;
    } catch (error) {
        throw error;
    }
};

const saveBook = async (vehicle) => {
    try {
        const response = await axios.post('http://localhost:8081/api/books', vehicle);
        return response.data;
    } catch (error) {
        throw error;
    }

}

export default {
    getBooksPaginated,
    saveBook
};