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
        // This is a workaround for libraries that are trying to access the element in which they operated via the
        // normal dom. The solution is to add the element in which the library operate to the actual dom by
        // appending elements to a slot of this element.
        const div = document.createElement('div');
        this.appendChild(div);
        // Passing the properties set from the server-side to the Image Editor
        this.imageEditor = new ImageEditor(div, {
            includeUI: {
                //https://stackoverflow.com/questions/54155431/toastui-image-editor-loadimagefromurl-doesnt-work
                loadImage: {
                    path: 'data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7',
                    name: 'Blank'
                },
                initMenu: this.initMenu ? this.initMenu : 'filter',
                menuBarPosition: this.menuBarPosition ? this.menuBarPosition : 'bottom',
                theme: this.theme === 'blackTheme' ? new BlackTheme().theme : new WhiteTheme().theme
            }
        });
    }

    // Method that will be called from the server-side to send the image data back to the server-side
    retrieveImageData() {
        this.imageData = this.imageEditor.toDataURL();
        this.$server.onImageDataUpdate(this.imageData);
    }

    // Method that will be called from the server-side to the the image on the client side
    setEditorImage() {
        this.imageEditor.loadImageFromURL(this.getAttribute("image-url"), "temporary-image-resource.png");
    }
}

customElements.define(TuiImageEditorWrapper.is, TuiImageEditorWrapper);
export {TuiImageEditorWrapper};
