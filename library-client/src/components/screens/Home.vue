<template>
  <div>
    <h1>Libros</h1>
    <b-card v-for="book in books" :key="book.id" :title="book.title" :sub-title="book.author">
      <b-card-text>
        {{
            book.title
        }}
      </b-card-text>
    </b-card>
    <div class="overflow-auto">
      <b-pagination
          v-model="currentPage"
          :per-page="perPage"
          aria-controls="my-table"
      ></b-pagination>

      <p class="mt-3">Página actual: {{ currentPage }}</p>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import bookService from "../../services/Books.js";

export default Vue.extend({
  name: "Home",
  data() {
    return {
      sortBy: "title",
      sortDesc: false,
      perPage: 5,
      currentPage: 1,
      books: [],
    };
  },

  methods: {
    async getBooks() {
      try {
        const data = await bookService.getBooksPaginated(
            parseInt(this.currentPage) - 1,
            parseInt(this.perPage),
            this.sortBy
        );
        this.books = data.content;
      } catch (error) {
        console.error(error);
      }
    },

    goToRoute(route) {
      this.$router.push(route);
    },
  },

  mounted() {
    this.getBooks();
  },
});

</script>

<style scoped>

</style>