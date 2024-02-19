import axios from 'axios';

let API_URL = 'http://localhost:8081/api/books/page';

const getBooksPaginated = async (page, size, sort) => {
    const params = {
        page,
        size, sort
    };
    try {
        const response = await axios.post(API_URL, null, {params});
        return response.data;
    } catch (error) {
        throw error;
    }
};

const findBooksByTitlePaginated = async (title, page, size, sort) => {
    const data = {
        title
    };
    const params = {
        page,
        size,
        sort
    };
    const config = {
        params: params,
        headers: {
            'Content-Type': 'application/json'
        }
    };
    try {
        const response = await axios.post('http://localhost:8081/api/books/book/title', JSON.stringify(data), config);
        return response.data;
    } catch (error) {
        console.error('Error al realizar la solicitud:', error);
        throw error;
    }
};

const findBooksByAuthorPaginated = async (author, page, size, sort) => {
    const data = {
        author
    };
    const params = {
        page,
        size,
        sort
    };
    const config = {
        params: params,
        headers: {
            'Content-Type': 'application/json'
        }
    };
    try {
        const response = await axios.post('http://localhost:8081/api/books/book/author', JSON.stringify(data), config);
        return response.data;
    } catch (error) {
        console.error('Error al realizar la solicitud:', error);
        throw error;
    }

}

const findBooksByGenrePaginated = async (genre, page, size, sort) => {
    const data = {
        genre
    };
    const params = {
        page,
        size,
        sort
    };
    const config = {
        params: params,
        headers: {
            'Content-Type': 'application/json'
        }
    };
    try {
        const response = await axios.post('http://localhost:8081/api/books/book/genre', JSON.stringify(data), config);
        return response.data;
    } catch (error) {
        console.error('Error al realizar la solicitud:', error);
        throw error;
    }
}

const findBooksByDateRangePaginated = async (start, end, page, size, sort) => {
    const data = {
        start,
        end
    };
    const params = {
        page,
        size,
        sort
    };
    const config = {
        params: params,
        headers: {
            'Content-Type': 'application/json'
        }
    };
    try {
        const response = await axios.post('http://localhost:8081/api/books/book/date/between', JSON.stringify(data), config);
        return response.data;
    } catch (error) {
        console.error('Error al realizar la solicitud:', error);
        throw error;
    }
}

const findBooksByDescendingPaginated = async (page, size, sort) => {
    const params = {
        page,
        size,
        sort
    };
    const config = {
        params: params,
        headers: {
            'Content-Type': 'application/json'
        }
    };
    try {
        const response = await axios.post('http://localhost:8081/api/books/book/date/descending', config);
        return response.data;
    } catch (error) {
        console.error('Error al realizar la solicitud:', error);
        throw error;
    }

}

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
    editBook,
    findBooksByTitlePaginated,
    findBooksByAuthorPaginated,
    findBooksByGenrePaginated,
    findBooksByDateRangePaginated,
    findBooksByDescendingPaginated
};