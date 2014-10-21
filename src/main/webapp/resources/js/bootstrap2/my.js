$(function () {
    PrimeFaces.widget.Dialog.prototype.originalHide = PrimeFaces.widget.Dialog.prototype.hide;
    PrimeFaces.widget.Dialog.prototype.hide = function () {
        var ajaxResponseArgs = arguments.callee.caller.arguments[2];
        if (ajaxResponseArgs && ajaxResponseArgs.validationFailed) {
            return;
        }
        this.originalHide();
    };
    PrimeFaces.widget.Dialog.prototype.originalShow = PrimeFaces.widget.Dialog.prototype.show;
    PrimeFaces.widget.Dialog.prototype.show = function () {
        var ajaxResponseArgs = arguments.callee.caller.arguments[2];
        if (ajaxResponseArgs && ajaxResponseArgs.validationFailed) {
            return;
        }
        this.originalShow();
    };
});