<template>
  <b-modal
      id="createBookModal"
      title="Registrar libro"
      no-close-on-backdrop
      no-close-on-esc
      hide-header-close
      @show="onShow"
      ok-title="Registrar"
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
                placeholder="yyyy"
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
                placeholder="mm"
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
                placeholder="dd"
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
  name: "CreateBookModal",
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
    async onSubmit() {
      const fullPublishDate = `${this.year}-${this.month}-${this.day}`;
      const book = {
        title: this.title,
        author: this.author,
        genre: this.genre,
        fullPublishDate,
      };
      await bookService.saveBook(book);
      this.$emit("book-created");
      this.$bvModal.hide("createBookModal");
    },

    onShow() {
      this.title = "";
      this.author = "";
      this.genre = "";
      this.year = "";
    },
  },
});
</script>

<style scoped>

</style>