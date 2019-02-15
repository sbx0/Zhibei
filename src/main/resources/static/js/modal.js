// 基础输入框
var base_input = {
    props: ["id", "name", "type", "placeholder", 'help'],
    data: function () {
        return {
            i18N: i18N,
        }
    },
    template: '<div class="form-group">\n    <label :for="id+\'_\'+type+\'_input\'">\n        {{name}}\n    </label>\n    <input\n            :id="id+\'_\'+type+\'_input\'"\n            :type="type"\n            :placeholder="placeholder"\n            class="form-control"\n    >\n    <small\n            :id="id+\'_\'+type+\'_input_help\'"\n            class="form-text text-muted"\n    >\n        {{help}}\n    </small>\n</div>',
};
// 基础表单
var base_table = {
    props: ["name", "body", "btn"],
    components: {
        "base-input": base_input,
    },
    data: function () {
        return {
            i18N: i18N,
        }
    },
    template: '<form :id="name+\'_form\'">\n    <div\n            v-for="b in body"\n    >\n        <base-input\n                :id="b.id"\n                :name="b.name"\n                :type="b.type"\n                :placeholder="b.placeholder"\n                :help="b.help"\n        >\n        </base-input>\n    </div>\n    <div v-html="btn"></div>\n</form>',
};
// 基础模态框
var base_modal = {
    props: ["name", "title", "body", "footer"],
    components: {
        "base-table": base_table,
    },
    data: function () {
        return {
            i18N: i18N,
        }
    },
    template: '<div\n        :id="name+\'Modal\'"\n        :aria-labelledby="name+\'ModalLabel\'"\n        class="modal fade"\n        tabindex="-1"\n        role="dialog"\n        aria-hidden="true"\n>\n    <div class="modal-dialog" role="document">\n        <div class="modal-content">\n            <div class="modal-header">\n                <h5 class="modal-title" :id="name+\'ModalLabel\'">\n                    {{title}}\n                </h5>\n                <button type="button" class="close" data-dismiss="modal" aria-label="Close">\n                    <span aria-hidden="true">&times;</span>\n                </button>\n            </div>\n            <div class="modal-body">\n                <base-table\n                        :name="name"\n                        :body="body"\n                ></base-table>\n            </div>\n            <div class="modal-footer" v-html="footer"></div>\n        </div>\n    </div>\n</div>',
};