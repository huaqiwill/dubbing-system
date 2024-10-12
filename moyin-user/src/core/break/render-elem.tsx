import { jsx, type VNode } from 'snabbdom'
import { SlateElement, type IDomEditor } from '@wangeditor/editor'
import type { Break } from './custom-types'
import { handleDeleteNode, handleSSMLRemarkClick } from '../helper'

export default {
  type: 'ssml-break',
  renderElem: (elem: SlateElement, _children: VNode[] | null, editor: IDomEditor): VNode => {
    const ssml_elem = elem as Break
    return (
      <span className="ssml-wrapper" contentEditable="false">
        <span
          className="remark"
          style={{ 'background-color': 'var(--ssml-break)' }}
          on={{
            mousedown: (event: Event) => event.preventDefault(),
            click: [handleSSMLRemarkClick(editor, ssml_elem)],
          }}
        >
          <span
            className="iconfont icon-roundclosefill"
            on={{ click: [handleDeleteNode(editor, ssml_elem)] }}
          ></span>
          <span className="data-content" attrs={{ 'data-content': ssml_elem.remark }}></span>
        </span>
        <span className="iconfont icon-tingdun" style={{ color: 'var(--ssml-break)' }}></span>
      </span>
    )
  },
}
