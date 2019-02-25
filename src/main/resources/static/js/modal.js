// 基础选择框
var base_select = {
    props: ["id", "name", "value", "type", 'multiple', 'disable', 'options', 'selected'],
    data: function () {
        return {
            i18N: i18N,
        }
    },
    computed: {
        option_selected: {
            get: function () {
                var option_selected = [];
                if (this.selected != null) {
                    for (var i = 0; i < this.options.length; i++) {
                        for (var j = 0; j < this.selected.length; j++) {
                            if (this.options[i].id.toString() === this.selected[j]) {
                                option_selected.push(this.options[i]);
                            }
                        }
                    }
                }
                return option_selected;
            },
        },
        option_unselected: {
            get: function () {
                var option_unselected = this.options.slice();
                if (this.selected != null) {
                    for (var i = 0; i < this.options.length; i++) {
                        for (var j = 0; j < this.selected.length; j++) {
                            if (this.options[i].id.toString() === this.selected[j]) {
                                var index = this.options.indexOf(this.options[i]);
                                option_unselected.splice(index, 1);
                            }
                        }
                    }
                }
                return option_unselected;
            },
        }
    },
    template: '<div class="form-group">\n    <label :for="id+\'_\'+type">\n        {{name}}\n    </label>\n    <select\n            :id="id+\'_\'+type"\n            :name="id"\n            :multiple="multiple"\n            :disable="disable"\n            v-if="multiple != null"\n            class="form-control"\n    >\n        <option\n                v-for="o in option_selected"\n                :value="o.id"\n                selected="selected"\n        >\n            {{o.id+\'[\'+o.name+\']\'}}\n        </option>\n        <option\n                v-for="o in option_unselected"\n                :value="o.id"\n        >\n            {{o.id+\'[\'+o.name+\']\'}}\n        </option>\n    </select>\n    <select\n            :id="id+\'_\'+type"\n            :name="id"\n            :multiple="multiple"\n            :value="selected"\n            :disable="disable"\n            v-else\n            class="form-control"\n    >\n        <option\n                v-for="o in option_unselected"\n                :value="o.id"\n        >\n            {{o.id+\'[\'+o.name+\']\'}}\n        </option>\n    </select>\n</div>',
};
// 基础输入框
var base_input = {
    props: ["id", "name", "value", "type", "placeholder", 'help', 'readonly'],
    data: function () {
        return {
            i18N: i18N,
        }
    },
    template: '<div class="form-group">\n    <label :for="id+\'_\'+type">\n        {{name}}\n    </label>\n    <input\n            :id="id+\'_\'+type"\n            :name="id"\n            :value="value"\n            :type="type"\n            :placeholder="i18N.please+i18N.input+name"\n            :readonly="readonly"\n            class="form-control"\n    >\n    <small\n            :id="id+\'_\'+type+\'_help\'"\n            class="form-text text-muted"\n    >\n        {{help}}\n    </small>\n</div>',
};
// 基础文本区域
var base_textarea = {
    props: ["id", "name", "value", "type", "placeholder", 'help', 'readonly'],
    data: function () {
        return {
            i18N: i18N,
        }
    },
    template: '<div class="form-group">\n    <label :for="id+\'_\'+type">\n        {{name}}\n    </label>\n    <textarea\n            :id="id+\'_\'+type"\n            :name="id"\n            :value="value"\n            :placeholder="i18N.please+i18N.input+name"\n            :readonly="readonly"\n            class="form-control"\n            rows="3"\n    ></textarea>\n</div>',
};
// 基础复选框
var base_checkbox = {
    props: ["id", "name", "value", "type", 'disabled'],
    data: function () {
        return {
            i18N: i18N,
        }
    },
    template: '<div class="form-check form-check-inline">\n    <input\n            :id="id+\'_\'+type"\n            :name="id"\n            value="true"\n            :checked="value"\n            :disabled="disabled"\n            type="checkbox"\n            class="form-check-input"\n    >\n    <label\n            class="form-check-label"\n            :for="id+\'_\'+type"\n    >{{name}}</label>\n</div>',
};
// 基础单选框
var base_radio = {
    props: ["id", "name", 'value', "type", 'disabled'],
    data: function () {
        return {
            i18N: i18N,
        }
    },
    template: '<div class="form-check form-check-inline">\n    <input\n            :id="id+\'_\'+type"\n            :name="id"\n            :value="value"\n            :checked="value"\n            :disabled="disabled"\n            type="radio"\n            class="form-check-input"\n    >\n    <label\n            :for="id+\'_\'+type"\n            class="form-check-label"\n    >{{name}}</label>\n</div>',
};
// 基础表单
var base_table = {
    props: ["name", "body", "btn"],
    components: {
        "base-input": base_input,
        "base-checkbox": base_checkbox,
        "base-radio": base_radio,
        "base-textarea": base_textarea,
        "base-select": base_select,
    },
    data: function () {
        return {
            i18N: i18N,
        }
    },
    template: '<form :id="name+\'_form\'">\n    <div\n            v-for="b in body"\n    >\n        <base-checkbox\n                v-if="b.type===\'checkbox\'"\n                :id="b.id"\n                :name="b.name"\n                :value="b.value"\n                :type="b.type"\n                :disable="b.disable"\n        >\n        </base-checkbox>\n        <base-radio\n                v-else-if="b.type===\'radio\'"\n                :id="b.id"\n                :name="b.name"\n                :value="b.value"\n                :type="b.type"\n                :disable="b.disable"\n        >\n        </base-radio>\n        <base-textarea\n                v-else-if="b.type===\'textarea\'"\n                :id="b.id"\n                :name="b.name"\n                :value="b.value"\n                :type="b.type"\n                :placeholder="b.placeholder"\n                :help="b.help"\n                :readonly="b.readonly"\n        >\n        </base-textarea>\n        <base-select\n                v-else-if="b.type===\'select\'"\n                :id="b.id"\n                :name="b.name"\n                :selected="b.selected"\n                :type="b.type"\n                :multiple="b.multiple"\n                :disable="b.disable"\n                :options="b.options"\n        ></base-select>\n        <base-input\n                v-else\n                :id="b.id"\n                :name="b.name"\n                :value="b.value"\n                :type="b.type"\n                :placeholder="b.placeholder"\n                :help="b.help"\n                :readonly="b.readonly"\n        >\n        </base-input>\n    </div>\n    <div v-html="btn"></div>\n</form>',
};
// 基础模态框
var base_modal = {
    props: ["name", "title", "body"],
    components: {
        "base-table": base_table,
    },
    data: function () {
        return {
            i18N: i18N,
        }
    },
    template: '<div\n        :id="name+\'Modal\'"\n        :aria-labelledby="name+\'ModalLabel\'"\n        class="modal fade"\n        tabindex="-1"\n        role="dialog"\n        aria-hidden="true"\n>\n    <div class="modal-dialog" role="document">\n        <div class="modal-content">\n            <div class="modal-header">\n                <h5 class="modal-title" :id="name+\'ModalLabel\'">\n                    {{title}}\n                </h5>\n                <button type="button" class="close" data-dismiss="modal" aria-label="Close">\n                    <span aria-hidden="true">&times;</span>\n                </button>\n            </div>\n            <div class="modal-body">\n                <base-table\n                        :name="name"\n                        :body="body"\n                ></base-table>\n            </div>\n            <div class="modal-footer">\n                <button type="button" class="btn btn-secondary" data-dismiss="modal">{{i18N.close}}</button>\n                <button :id="name+\'ModalSubmitBtn\'" type="button" class="btn btn-primary">{{i18N.save}}</button>\n            </div>\n        </div>\n    </div>\n</div>',
};