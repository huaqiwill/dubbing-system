import { DomEditor, type IDomEditor } from '@wangeditor/editor'

export default <T extends IDomEditor>(editor: T) => {
  const { isInline, isVoid } = editor
  const newEditor = editor

  newEditor.isInline = (elem) => {
    const type = DomEditor.getNodeType(elem)
    if (type === 'paragraph') return false
    return isInline(elem)
  }

  newEditor.isVoid = (elem) => {
    const type = DomEditor.getNodeType(elem)
    if (type === 'paragraph') return false
    return isVoid(elem)
  }

  return newEditor
}
