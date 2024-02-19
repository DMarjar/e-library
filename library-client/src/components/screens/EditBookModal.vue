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
        <b-form-select
            class="form-select"
            id="genre"
            v-model="genre"
            :options="genreList"
            required
        >
          <template #first>
            <option :value="null" disabled>Seleccione un género</option>
          </template>
        </b-form-select>
      </b-form-group>
      <b-row no-gutters>
        <b-col cols="4">
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
        </b-col>
        <b-col cols="4">
          <b-form-group
              label="Mes"
              label-for="month"
          >
            <b-form-input
                id="month"
                type="number"
                v-model="month"
                required
            ></b-form-input>
          </b-form-group>
        </b-col>
        <b-col cols="4">
          <b-form-group
              label="Día"
              label-for="day"
          >
            <b-form-input
                id="day"
                type="number"
                v-model="day"
                required
            ></b-form-input>
          </b-form-group>
        </b-col>
      </b-row>
    </b-form>
  </b-modal>
</template>

<script>
import Vue from "vue";
import bookService from "../../services/Books.js";
import genreList from "@/utils/GenreList";

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
      // Book
      title: "",
      author: "",
      genre: "",
      year: "",
      month: "",
      day: "",

      // Genre list
      genreList,
    }
  },

  methods: {
    onShow() {
      this.title = this.book.title;
      this.author = this.book.author;
      this.genre = this.book.genre;
      const publishDate = this.book.fullPublishDate.split("-");
      this.year = publishDate[0];
      this.month = publishDate[1];
      this.day = publishDate[2];
    },

    async onSubmit() {
      const fullPublishDate = `${this.year}-${this.month}-${this.day}`;
      const book = {
        id: this.book.id,
        title: this.title,
        author: this.author,
        genre: this.genre,
        fullPublishDate,
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