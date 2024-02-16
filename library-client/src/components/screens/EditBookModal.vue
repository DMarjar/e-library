<template>
  <b-modal
      id="editBookModal"
      title="Editar libro"
      no-close-on-backdrop
      no-close-on-esc
      hide-header-close
      @show="onShow"
      ok-title="Guardar"
      @ok="onSubmit"
      cancel-title="Cancelar"
  >
    <b-form>
      <b-form-group
          label="Título"
          label-for="title"
      >
        <b-form-input
            id="title"
            type="text"
            v-model="title"
            required
        ></b-form-input>
      </b-form-group>
      <b-form-group
          label="Autor"
          label-for="author"
      >
        <b-form-input
            id="author"
            type="text"
            v-model="author"
            required
        ></b-form-input>
      </b-form-group>
      <b-form-group
          label="Género"
          label-for="genre"
      >
        <b-form-input
            id="genre"
            type="text"
            v-model="genre"
            required
        ></b-form-input>
      </b-form-group>
      <b-form-group
          label="Año"
          label-for="year"
      >
        <b-form-input
            id="year"
            type="number"
            v-model="year"
            required
        ></b-form-input>
      </b-form-group>
    </b-form>
  </b-modal>
</template>

<script>
import Vue from "vue";
import bookService from "../../services/Books.js";

export default Vue.extend({
  name: "EditBookModal",
  props: {
    book: {
      type: Object,
      required: false,
    },
  },
  data() {
    return {
      title: "",
      author: "",
      genre: "",
      year: "",
    }
  },

  methods: {
    onShow() {
      this.title = this.book.title;
      this.author = this.book.author;
      this.genre = this.book.genre;
      this.year = this.book.year;
    },
    async onSubmit() {
      const book = {
        id: this.book.id,
        title: this.title,
        author: this.author,
        genre: this.genre,
        year: this.year,
      };
      await bookService.editBook(book);
      this.$emit("book-updated");
      this.$bvModal.hide("editBookModal");
    },
  },
});

</script>


<style scoped>

</style>