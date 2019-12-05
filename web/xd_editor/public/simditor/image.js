(function() {
    var ImageButton, ImagePopover,
        __hasProp = {}.hasOwnProperty,
        __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; },
        __slice = [].slice;

    ImageButton = (function(_super) {
        __extends(ImageButton, _super);

        function ImageButton() {
            return ImageButton.__super__.constructor.apply(this, arguments);
        }

        ImageButton.prototype.name = 'image';

        ImageButton.prototype.icon = 'picture-o';

        ImageButton.prototype.htmlTag = 'img';

        ImageButton.prototype.disableTag = 'pre, table';

        ImageButton.prototype.defaultImage = '';

        ImageButton.prototype.needFocus = false;

        ImageButton.prototype._init = function() {
            var item, _i, _len, _ref;
            if (this.editor.opts.imageButton) {
                if (Array.isArray(this.editor.opts.imageButton)) {
                    this.menu = [];
                    _ref = this.editor.opts.imageButton;
                    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                        item = _ref[_i];
                        this.menu.push({
                            name: item + '-image',
                            text: this._t(item + 'Image')
                        });
                    }
                } else {
                    this.menu = false;
                }
            } else {
                if (this.editor.uploader != null) {
                    this.menu = [
                        {
                            name: 'upload-image',
                            text: this._t('uploadImage')
                        }, {
                            name: 'external-image',
                            text: this._t('externalImage')
                        }
                    ];
                } else {
                    this.menu = false;
                }
            }
            this.defaultImage = this.editor.opts.defaultImage;
            this.editor.body.on('click', 'img:not([data-non-image])', (function(_this) {
                return function(e) {
                    var $img, range;
                    $img = $(e.currentTarget);
                    range = document.createRange();
                    range.selectNode($img[0]);
                    _this.editor.selection.selectRange(range);
                    if (!_this.editor.util.support.onselectionchange) {
                        _this.editor.trigger('selectionchanged');
                    }
                    return false;
                };
            })(this));
            this.editor.body.on('mouseup', 'img:not([data-non-image])', (function(_this) {
                return function(e) {
                    return false;
                };
            })(this));
            this.editor.on('selectionchanged.image', (function(_this) {
                return function() {
                    var $contents, $img, range;
                    range = _this.editor.selection.getRange();
                    if (range == null) {
                        return;
                    }
                    $contents = $(range.cloneContents()).contents();
                    if ($contents.length === 1 && $contents.is('img:not([data-non-image])')) {
                        $img = $(range.startContainer).contents().eq(range.startOffset);
                        return _this.popover.show($img);
                    } else {
                        return _this.popover.hide();
                    }
                };
            })(this));
            this.editor.on('valuechanged.image', (function(_this) {
                return function() {
                    var $masks;
                    $masks = _this.editor.wrapper.find('.simditor-image-loading');
                    if (!($masks.length > 0)) {
                        return;
                    }
                    return $masks.each(function(i, mask) {
                        var $img, $mask, file;
                        $mask = $(mask);
                        $img = $mask.data('img');
                        if (!($img && $img.parent().length > 0)) {
                            $mask.remove();
                            if ($img) {
                                file = $img.data('file');
                                if (file) {
                                    _this.editor.uploader.cancel(file);
                                    if (_this.editor.body.find('img.uploading').length < 1) {
                                        return _this.editor.uploader.trigger('uploadready', [file]);
                                    }
                                }
                            }
                        }
                    });
                };
            })(this));
            return ImageButton.__super__._init.call(this);
        };

        ImageButton.prototype.render = function() {
            var args;
            args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
            ImageButton.__super__.render.apply(this, args);
            this.popover = new ImagePopover({
                button: this
            });
            if (this.editor.opts.imageButton === 'upload') {
                return this._initUploader(this.el);
            }
        };

        ImageButton.prototype.renderMenu = function() {
            ImageButton.__super__.renderMenu.call(this);
            return this._initUploader();
        };

        ImageButton.prototype._initUploader = function($uploadItem) {
            var $input, createInput;
            if ($uploadItem == null) {
                $uploadItem = this.menuEl.find('.menu-item-upload-image');
            }
            if (this.editor.uploader == null) {
                this.el.find('.btn-upload').remove();
                return;
            }
            $input = null;
            createInput = (function(_this) {
                return function() {
                    if ($input) {
                        $input.remove();
                    }
                    return $input = $('<input type="file" title="' + _this._t('uploadImage') + '" accept="image/*">').appendTo($uploadItem);
                };
            })(this);
            createInput();
            $uploadItem.on('click mousedown', 'input[type=file]', (function(_this) {
                return function(e) {
                    return e.stopPropagation();
                };
            })(this));
            $uploadItem.on('change', 'input[type=file]', (function(_this) {
                return function(e) {
                    if (_this.editor.inputManager.focused) {
                        _this.editor.uploader.upload($input, {
                            inline: true
                        });
                        createInput();
                    } else {
                        _this.editor.one('focus', function(e) {
                            _this.editor.uploader.upload($input, {
                                inline: true
                            });
                            return createInput();
                        });
                        _this.editor.focus();
                    }
                    return _this.wrapper.removeClass('menu-on');
                };
            })(this));
            this.editor.uploader.on('beforeupload', (function(_this) {
                return function(e, file) {
                    var $img;
                    if (!file.inline) {
                        return;
                    }
                    if (file.img) {
                        $img = $(file.img);
                    } else {
                        $img = _this.createImage(file.name);
                        file.img = $img;
                    }
                    $img.addClass('uploading');
                    $img.data('file', file);
                    return _this.editor.uploader.readImageFile(file.obj, function(img) {
                        var src;
                        if (!$img.hasClass('uploading')) {
                            return;
                        }
                        src = img ? img.src : _this.defaultImage;
                        return _this.loadImage($img, src, function() {
                            if (_this.popover.active) {
                                _this.popover.refresh();
                                return _this.popover.srcEl.val(_this._t('uploading')).prop('disabled', true);
                            }
                        });
                    });
                };
            })(this));
            this.editor.uploader.on('uploadprogress', $.proxy(this.editor.util.throttle(function(e, file, loaded, total) {
                var $img, $mask, percent;
                if (!file.inline) {
                    return;
                }
                $mask = file.img.data('mask');
                if (!$mask) {
                    return;
                }
                $img = $mask.data('img');
                if (!($img.hasClass('uploading') && $img.parent().length > 0)) {
                    $mask.remove();
                    return;
                }
                percent = loaded / total;
                percent = (percent * 100).toFixed(0);
                if (percent > 99) {
                    percent = 99;
                }
                return $mask.find('.progress').height("" + (100 - percent) + "%");
            }, 500), this));
            this.editor.uploader.on('uploadsuccess', (function(_this) {
                return function(e, file, result) {
                    var $img, $mask, msg;
                    if (!file.inline) {
                        return;
                    }
                    $img = file.img;
                    if (!($img.hasClass('uploading') && $img.parent().length > 0)) {
                        return;
                    }
                    $img.removeData('file');
                    $img.removeClass('uploading').removeClass('loading');
                    $mask = $img.data('mask');
                    if ($mask) {
                        $mask.remove();
                    }
                    $img.removeData('mask');
                    if (typeof result !== 'object') {
                        try {
                            result = $.parseJSON(result);
                        } catch (_error) {
                            e = _error;
                            result = {
                                success: false
                            };
                        }
                    }
                    if (result.success === false) {
                        msg = result.msg || _this._t('uploadFailed');
                        alert(msg);
                        $img.attr('src', _this.defaultImage);
                    } else {
                        $img.attr('src', result.file_path);
                    }
                    if (_this.popover.active) {
                        _this.popover.srcEl.prop('disabled', false);
                        _this.popover.srcEl.val(result.file_path);
                    }
                    _this.editor.trigger('valuechanged');
                    if (_this.editor.body.find('img.uploading').length < 1) {
                        return _this.editor.uploader.trigger('uploadready', [file, result]);
                    }
                };
            })(this));
            return this.editor.uploader.on('uploaderror', (function(_this) {
                return function(e, file, xhr) {
                    var $img, $mask, msg, result;
                    if (!file.inline) {
                        return;
                    }
                    if (xhr.statusText === 'abort') {
                        return;
                    }
                    if (xhr.responseText) {
                        try {
                            result = $.parseJSON(xhr.responseText);
                            msg = result.msg;
                        } catch (_error) {
                            e = _error;
                            msg = _this._t('uploadError');
                        }
                        alert(msg);
                    }
                    $img = file.img;
                    if (!($img.hasClass('uploading') && $img.parent().length > 0)) {
                        return;
                    }
                    $img.removeData('file');
                    $img.removeClass('uploading').removeClass('loading');
                    $mask = $img.data('mask');
                    if ($mask) {
                        $mask.remove();
                    }
                    $img.removeData('mask');
                    $img.attr('src', _this.defaultImage);
                    if (_this.popover.active) {
                        _this.popover.srcEl.prop('disabled', false);
                        _this.popover.srcEl.val(_this.defaultImage);
                    }
                    _this.editor.trigger('valuechanged');
                    if (_this.editor.body.find('img.uploading').length < 1) {
                        return _this.editor.uploader.trigger('uploadready', [file, result]);
                    }
                };
            })(this));
        };

        ImageButton.prototype.status = function($node) {
            if ($node != null) {
                this.setDisabled($node.is(this.disableTag));
            }
            if (this.disabled) {
                return true;
            }
        };

        ImageButton.prototype.loadImage = function($img, src, callback) {
            var $mask, img, positionMask;
            positionMask = (function(_this) {
                return function() {
                    var imgOffset, wrapperOffset;
                    imgOffset = $img.offset();
                    wrapperOffset = _this.editor.wrapper.offset();
                    return $mask.css({
                        top: imgOffset.top - wrapperOffset.top,
                        left: imgOffset.left - wrapperOffset.left,
                        width: $img.width(),
                        height: $img.height()
                    }).show();
                };
            })(this);
            $img.addClass('loading');
            $mask = $img.data('mask');
            if (!$mask) {
                $mask = $('<div class="simditor-image-loading"><div class="progress"></div></div>').hide().appendTo(this.editor.wrapper);
                positionMask();
                $img.data('mask', $mask);
                $mask.data('img', $img);
            }
            img = new Image();
            img.onload = (function(_this) {
                return function() {
                    var height, width;
                    if (!$img.hasClass('loading') && !$img.hasClass('uploading')) {
                        return;
                    }
                    width = img.width;
                    height = img.height;
                    $img.attr({
                        src: src,
                        'data-image-size': width + ',' + height
                    }).removeClass('loading');
                    if ($img.hasClass('uploading')) {
                        _this.editor.util.reflow(_this.editor.body);
                        positionMask();
                    } else {
                        $mask.remove();
                        $img.removeData('mask');
                    }
                    return callback(img);
                };
            })(this);
            img.onerror = (function(_this) {
                return function() {
                    callback(false);
                    $mask.remove();
                    return $img.removeData('mask').removeClass('loading');
                };
            })(this);
            return img.src = src;
        };

        ImageButton.prototype.createImage = function(name) {
            var $block, $img, $nextBlock, range;
            if (name == null) {
                name = 'Image';
            }
            if (!this.editor.inputManager.focused) {
                this.editor.focus();
            }
            range = this.editor.selection.getRange();
            range.deleteContents();
            $block = this.editor.util.closestBlockEl();
            if ($block.is('p') && !this.editor.util.isEmptyNode($block)) {
                $block = $('<p/>').append(this.editor.util.phBr).insertAfter($block);
                this.editor.selection.setRangeAtStartOf($block, range);
            }
            $img = $('<img/>').attr('alt', name);
            range.insertNode($img[0]);
            $nextBlock = $block.next('p');
            if (!($nextBlock.length > 0)) {
                $nextBlock = $('<p/>').append(this.editor.util.phBr).insertAfter($block);
            }
            this.editor.selection.setRangeAtStartOf($nextBlock);
            return $img;
        };

        ImageButton.prototype.command = function(src) {
            var $img;
            $img = this.createImage();
            return this.loadImage($img, src || this.defaultImage, (function(_this) {
                return function() {
                    _this.editor.trigger('valuechanged');
                    _this.editor.util.reflow($img);
                    $img.click();
                    return _this.popover.one('popovershow', function() {
                        _this.popover.srcEl.focus();
                        return _this.popover.srcEl[0].select();
                    });
                };
            })(this));
        };

        return ImageButton;

    })(Button);

    ImagePopover = (function(_super) {
        __extends(ImagePopover, _super);

        function ImagePopover() {
            return ImagePopover.__super__.constructor.apply(this, arguments);
        }

        ImagePopover.prototype.offset = {
            top: 6,
            left: -4
        };

        ImagePopover.prototype.render = function() {
            var tpl;
            tpl = "<div class=\"link-settings\">\n  <div class=\"settings-field\">\n    <label>" + (this._t('imageUrl')) + "</label>\n    <input class=\"image-src\" type=\"text\" tabindex=\"1\" />\n    <a class=\"btn-upload\" href=\"javascript:;\" title=\"" + (this._t('uploadImage')) + "\" tabindex=\"-1\">\n      <span class=\"simditor-icon simditor-icon-upload\"></span>\n    </a>\n  </div>\n  <div class='settings-field'>\n    <label>" + (this._t('imageAlt')) + "</label>\n    <input class=\"image-alt\" id=\"image-alt\" type=\"text\" tabindex=\"1\" />\n  </div>\n  <div class=\"settings-field\">\n    <label>" + (this._t('imageSize')) + "</label>\n    <input class=\"image-size\" id=\"image-width\" type=\"text\" tabindex=\"2\" />\n    <span class=\"times\">×</span>\n    <input class=\"image-size\" id=\"image-height\" type=\"text\" tabindex=\"3\" />\n    <a class=\"btn-restore\" href=\"javascript:;\" title=\"" + (this._t('restoreImageSize')) + "\" tabindex=\"-1\">\n      <span class=\"simditor-icon simditor-icon-undo\"></span>\n    </a>\n  </div>\n</div>";
            this.el.addClass('image-popover').append(tpl);
            this.srcEl = this.el.find('.image-src');
            this.widthEl = this.el.find('#image-width');
            this.heightEl = this.el.find('#image-height');
            this.altEl = this.el.find('#image-alt');
            this.srcEl.on('keydown', (function(_this) {
                return function(e) {
                    if (!(e.which === 13 && !_this.target.hasClass('uploading'))) {
                        return;
                    }
                    e.preventDefault();
                    _this.button.editor.body.focus();
                    _this.button.editor.selection.setRangeAfter(_this.target);
                    return _this.hide();
                };
            })(this));
            this.srcEl.on('blur', (function(_this) {
                return function(e) {
                    return _this._loadImage(_this.srcEl.val());
                };
            })(this));
            this.el.find('.image-size').on('blur', (function(_this) {
                return function(e) {
                    _this._resizeImg($(e.currentTarget));
                    return _this.el.data('popover').refresh();
                };
            })(this));
            this.el.find('.image-size').on('keyup', (function(_this) {
                return function(e) {
                    var inputEl;
                    inputEl = $(e.currentTarget);
                    if (!(e.which === 13 || e.which === 27 || e.which === 9)) {
                        return _this._resizeImg(inputEl, true);
                    }
                };
            })(this));
            this.el.find('.image-size').on('keydown', (function(_this) {
                return function(e) {
                    var inputEl;
                    inputEl = $(e.currentTarget);
                    if (e.which === 13 || e.which === 27) {
                        e.preventDefault();
                        if (e.which === 13) {
                            _this._resizeImg(inputEl);
                        } else {
                            _this._restoreImg();
                        }
                        _this.button.editor.body.focus();
                        _this.button.editor.selection.setRangeAfter(_this.target);
                        return _this.hide();
                    } else if (e.which === 9) {
                        return _this.el.data('popover').refresh();
                    }
                };
            })(this));
            this.altEl.on('keydown', (function(_this) {
                return function(e) {
                    if (e.which === 13) {
                        e.preventDefault();
                        _this.button.editor.body.focus();
                        _this.button.editor.selection.setRangeAfter(_this.target);
                        return _this.hide();
                    }
                };
            })(this));
            this.altEl.on('keyup', (function(_this) {
                return function(e) {
                    if (e.which === 13 || e.which === 27 || e.which === 9) {
                        return;
                    }
                    _this.alt = _this.altEl.val();
                    return _this.target.attr('alt', _this.alt);
                };
            })(this));
            this.el.find('.btn-restore').on('click', (function(_this) {
                return function(e) {
                    _this._restoreImg();
                    return _this.el.data('popover').refresh();
                };
            })(this));
            this.editor.on('valuechanged', (function(_this) {
                return function(e) {
                    if (_this.active) {
                        return _this.refresh();
                    }
                };
            })(this));
            return this._initUploader();
        };

        ImagePopover.prototype._initUploader = function() {
            var $uploadBtn, createInput;
            $uploadBtn = this.el.find('.btn-upload');
            if (this.editor.uploader == null) {
                $uploadBtn.remove();
                return;
            }
            createInput = (function(_this) {
                return function() {
                    if (_this.input) {
                        _this.input.remove();
                    }
                    return _this.input = $('<input type="file" title="' + _this._t('uploadImage') + '" accept="image/*">').appendTo($uploadBtn);
                };
            })(this);
            createInput();
            this.el.on('click mousedown', 'input[type=file]', (function(_this) {
                return function(e) {
                    return e.stopPropagation();
                };
            })(this));
            return this.el.on('change', 'input[type=file]', (function(_this) {
                return function(e) {
                    _this.editor.uploader.upload(_this.input, {
                        inline: true,
                        img: _this.target
                    });
                    return createInput();
                };
            })(this));
        };

        ImagePopover.prototype._resizeImg = function(inputEl, onlySetVal) {
            var height, value, width;
            if (onlySetVal == null) {
                onlySetVal = false;
            }
            value = inputEl.val() * 1;
            if (!($.isNumeric(value) || value < 0)) {
                return;
            }
            if (inputEl.is(this.widthEl)) {
                height = this.height * value / this.width;
                this.heightEl.val(height);
            } else {
                width = this.width * value / this.height;
                this.widthEl.val(width);
            }
            if (!onlySetVal) {
                this.target.attr({
                    width: width || value,
                    height: height || value
                });
            }
            return this.editor.trigger('valuechanged');
        };

        ImagePopover.prototype._restoreImg = function() {
            var size, _ref;
            size = ((_ref = this.target.data('image-size')) != null ? _ref.split(",") : void 0) || [this.width, this.height];
            this.target.attr({
                width: size[0] * 1,
                height: size[1] * 1
            });
            this.widthEl.val(size[0]);
            this.heightEl.val(size[1]);
            return this.editor.trigger('valuechanged');
        };

        ImagePopover.prototype._loadImage = function(src, callback) {
            if (/^data:image/.test(src) && !this.editor.uploader) {
                if (callback) {
                    callback(false);
                }
                return;
            }
            return this.button.loadImage(this.target, src, (function(_this) {
                return function(img) {
                    var blob;
                    if (!img) {
                        return;
                    }
                    if (_this.active) {
                        _this.width = img.width;
                        _this.height = img.height;
                        _this.widthEl.val(_this.width);
                        _this.heightEl.val(_this.height);
                        _this.target.removeAttr('width').removeAttr('height');
                    }
                    if (/^data:image/.test(src)) {
                        blob = _this.editor.util.dataURLtoBlob(src);
                        blob.name = "Base64 Image.png";
                        _this.editor.uploader.upload(blob, {
                            inline: true,
                            img: _this.target
                        });
                    } else {
                        _this.editor.trigger('valuechanged');
                    }
                    if (callback) {
                        return callback(img);
                    }
                };
            })(this));
        };

        ImagePopover.prototype.show = function() {
            var $img, args;
            args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
            ImagePopover.__super__.show.apply(this, args);
            $img = this.target;
            this.width = $img.width();
            this.height = $img.height();
            this.alt = $img.attr('alt');
            if ($img.hasClass('uploading')) {
                return this.srcEl.val(this._t('uploading')).prop('disabled', true);
            } else {
                this.srcEl.val($img.attr('src')).prop('disabled', false);
                this.widthEl.val(this.width);
                this.heightEl.val(this.height);
                return this.altEl.val(this.alt);
            }
        };

        return ImagePopover;

    })(Popover);

    Simditor.Toolbar.addButton(ImageButton);

}).call(this);
