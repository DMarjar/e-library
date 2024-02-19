<template>
  <b-modal
      id="dateSearchModal"
      title="Busqueda avanzada por fechas"
      no-close-on-backdrop
      no-close-on-esc
      hide-header-close
      @show="onShow"
      ok-title="Buscar"
      @ok="onSubmit"
      cancel-title="Cancelar"
  >
    <b-form>
      <b-form-group label="Buscar por..." v-slot="{ ariaDescribedby }">
        <b-form-radio v-model="selected" value="descending">Descendiente</b-form-radio>
        <b-form-radio v-model="selected" value="dateRange">Por rango de fechas</b-form-radio>
      </b-form-group>
      <hr v-if="selected === 'dateRange'">
      <b-row v-if="selected === 'dateRange'" no-gutters>
        <b-col cols="12" class="mb-3">
          <b-row no-gutters>
            <b>
              Fecha de inicio
            </b>
            <b-col cols="4">
              <b-form-group
                  label="Año"
                  label-for="year-start"
              >
                <b-form-input
                    id="year-start"
                    type="number"
                    placeholder="yyyy"
                    v-model="yearStart"
                    required
                ></b-form-input>
              </b-form-group>
            </b-col>
            <b-col cols="4">
              <b-form-group
                  label="Mes"
                  label-for="month-start"
              >
                <b-form-input
                    id="month-start"
                    type="number"
                    placeholder="mm"
                    v-model="monthStart"
                    required
                ></b-form-input>
              </b-form-group>
            </b-col>
            <b-col cols="4">
              <b-form-group
                  label="Día"
                  label-for="day-start"
              >
                <b-form-input
                    id="day-start"
                    type="number"
                    placeholder="dd"
                    v-model="dayStart"
                    required
                ></b-form-input>
              </b-form-group>
            </b-col>
          </b-row>
        </b-col>
        <b-col cols="12">
          <b-row no-gutters>
            <b>
              Fecha de fin
            </b>
            <b-col cols="4">
              <b-form-group
                  label="Año"
                  label-for="year-end"
              >
                <b-form-input
                    id="year-end"
                    type="number"
                    placeholder="yyyy"
                    v-model="yearEnd"
                    required
                ></b-form-input>
              </b-form-group>
            </b-col>
            <b-col cols="4">
              <b-form-group
                  label="Mes"
                  label-for="month-end"
              >
                <b-form-input
                    id="month-end"
                    type="number"
                    placeholder="mm"
                    v-model="monthEnd"
                    required
                ></b-form-input>
              </b-form-group>
            </b-col>
            <b-col cols="4">
              <b-form-group
                  label="Día"
                  label-for="day-end"
              >
                <b-form-input
                    id="day-end"
                    type="number"
                    placeholder="dd"
                    v-model="dayEnd"
                    required
                ></b-form-input>
              </b-form-group>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
    </b-form>
  </b-modal>
</template>

<script>
import Vue from "vue";
import bookService from "../../services/Books.js";

export default Vue.extend({
  name: "DateSearchModal",
  data() {
    return {
      // Date start
      yearStart: "",
      monthStart: "",
      dayStart: "",

      // Date end
      yearEnd: "",
      monthEnd: "",
      dayEnd: "",

      // Radio
      selected: "",
    };
  },

  methods: {
    onShow() {
      this.year = "";
      this.month = "";
      this.day = "";
      this.selected = "";
    },

    async onSubmit() {
      if (this.selected === "dateRange") {
        const dateStart = `${this.yearStart}-${this.monthStart}-${this.dayStart}`;
        const dateEnd = `${this.yearEnd}-${this.monthEnd}-${this.dayEnd}`;

        const dateRangeObject = {
          dateStart,
          dateEnd,
        }
        this.$emit("searchByDateRange", dateRangeObject);

      } else if (this.selected === "descending") {
        this.$emit("searchByDescending");
      } else {
        this.$bvModal.hide("dateSearchModal");
      }
    },
  },
});
</script>


<style scoped>

</style>