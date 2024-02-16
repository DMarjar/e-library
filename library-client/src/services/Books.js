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

const saveBook = async (book) => {
    try {
        const response = await axios.post('http://localhost:8081/api/books/book', book);
        return response.data;
    } catch (error) {
        throw error;
    }

}

const deleteBook = async (id) => {
    try {
        const response = await axios.delete('http://localhost:8081/api/books/book/' + id);
        return response.data;
    } catch (error) {
        throw error;
    }
}

const editBook = async (book) => {
    const id = book.id;
    try {
        const response = await axios.put('http://localhost:8081/api/books/book/' + id, book);
        return response.data;
    } catch (error) {
        throw error;
    }
}

export default {
    getBooksPaginated,
    saveBook,
    deleteBook,
    editBook
};