import {PolymerElement} from '@polymer/polymer/polymer-element.js';
import {html} from '@polymer/polymer/lib/utils/html-tag.js';
import '@polymer/polymer/lib/utils/html-tag.js';
import 'tui-image-editor/dist/tui-image-editor.css';
import ImageEditor from 'tui-image-editor';
import {WhiteTheme} from "./tui-image-editor-white-theme";
import {BlackTheme} from "./tui-image-editor-black-theme";

class TuiImageEditorWrapper extends PolymerElement {
    static get template() {
        return html`
            <div style="width: 100%;height: 100%;">
                <slot></slot>
            </div>
        `;
    }

    static get is() {
        return 'tui-image-editor-wrapper'
    }

    static get properties() {
        return {
            theme: {
                type: String
            },
            initMenu: {
                type: String
            },
            menuBarPosition: {
                type: String
            },
            imageData: {
                type: String
            },
            imageUrl: {
                type: String
            },
            imageEditor: {
                type: Object
            }
        }
    }

    ready() {
        super.ready();
        const initMenu = this.initMenu;
        const menuBarPosition = this.menuBarPosition;
        const div = document.createElement('div');
        this.appendChild(div);
        this.imageEditor = new ImageEditor(div, {
            includeUI: {
                //https://stackoverflow.com/questions/54155431/toastui-image-editor-loadimagefromurl-doesnt-work
                loadImage: {
                    path: 'data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7',
                    name: 'Blank'
                },
                initMenu: initMenu ? initMenu : 'filter',
                menuBarPosition: menuBarPosition ? menuBarPosition : 'bottom',
                theme: this.theme === 'blackTheme' ? new BlackTheme().theme : new WhiteTheme().theme
            }
        });
    }

    retrieveImageData() {
        this.imageData = this.imageEditor.toDataURL();
        this.$server.onImageDataUpdate(this.imageData);
    }

    setEditorImage() {
        this.imageEditor.loadImageFromURL(this.getAttribute("image-url"), "temporary-image-resource.png");
    }
}

customElements.define(TuiImageEditorWrapper.is, TuiImageEditorWrapper);
export {TuiImageEditorWrapper};
